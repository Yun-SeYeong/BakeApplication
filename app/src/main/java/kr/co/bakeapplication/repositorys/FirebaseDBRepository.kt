package kr.co.bakeapplication.repositorys

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kr.co.bakeapplication.data.Recipe

class FirebaseDBRepository {
    interface OnWriteRecipeListener{
        fun OnComplete()
    }

    private val _dbRef: DatabaseReference by lazy {
        Firebase.database.reference
    }

    private val _auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val _storageRepository: FirebaseStorageRepository by lazy {
        FirebaseStorageRepository()
    }

    fun writeRecipe(recipe: Recipe, listener: OnWriteRecipeListener) {
        val key = _dbRef.child("recipes").push().key
        recipe.creatoruid = _auth.uid!!

        var progress = recipe.pages!!.size + 1

        fun proccess() {
            runBlocking {
                if (--progress == 0) {
                    listener.OnComplete()
                }
            }
        }

        _storageRepository.uploadFile("RecipeImage/$key/thumbnail", recipe.thumbNailUri).addOnCompleteListener {
            _storageRepository.getDownloadUrl("RecipeImage/$key/thumbnail").addOnCompleteListener {
                recipe.thumbNailUri = it.result.toString()
                proccess()
            }
        }

        for((i, page) in recipe.pages!!.withIndex()) {
            Log.d("BaseActivity", page.imageUri)
            _storageRepository.uploadFile("RecipeImage/$key/page$i", page.imageUri).addOnCompleteListener {
                _storageRepository.getDownloadUrl("RecipeImage/$key/page$i").addOnCompleteListener {
                    recipe.pages!![i].imageUri = it.result.toString()
                    val postValue = recipe.toMap()
                    val childUpdate = hashMapOf<String, Any>(
                        "/recipes/$key" to postValue
                    )
                    _dbRef.updateChildren(childUpdate)
                    proccess()
                }
            }
        }
    }

    fun readRecipes(listener: ValueEventListener) {
        _dbRef.child("recipes").addListenerForSingleValueEvent(listener)
    }

    fun readMyRecipes(listener: ValueEventListener) {
        Log.d("BaseActivity", "uid : " + _auth.uid)
        _dbRef.child("recipes").orderByChild("creatoruid").equalTo(_auth.uid).addListenerForSingleValueEvent(listener)
    }
}