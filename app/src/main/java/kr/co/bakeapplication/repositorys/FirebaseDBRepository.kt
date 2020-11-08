package kr.co.bakeapplication.repositorys

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kr.co.bakeapplication.data.Profile
import kr.co.bakeapplication.data.Recipe

class FirebaseDBRepository {
    interface OnWriteRecipeListener {
        fun OnComplete()
    }

    interface OnWriteProfileListener {
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

    fun createProfile(profileInfo: Profile, listener: OnWriteProfileListener) {
        val key = _dbRef.child("recipes").push().key
        profileInfo.firebasetoken = _auth.uid

        if (profileInfo.profileImg != "") {
            _storageRepository.uploadFile("UserImage/$key/profileImage", profileInfo.profileImg!!)
                .addOnCompleteListener {
                    _storageRepository.getDownloadUrl("UserImage/$key/profileImage")
                        .addOnCompleteListener {
                            profileInfo.profileImg = it.result.toString()

                            val postValue = profileInfo.toMap()
                            val childUpdate = hashMapOf<String, Any>(
                                "/users/$key" to postValue
                            )
                            _dbRef.updateChildren(childUpdate)

                            listener.OnComplete()
                        }
                }
        } else {
            val postValue = profileInfo.toMap()
            val childUpdate = hashMapOf<String, Any>(
                "/users/$key" to postValue
            )
            _dbRef.updateChildren(childUpdate)

            listener.OnComplete()
        }
    }

    fun updateProfile(profileInfo: Profile, listener: OnWriteProfileListener) {
        _dbRef.child("users")
            .orderByChild("firebasetoken")
            .equalTo(_auth.uid)
            .limitToFirst(1)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.getValue<Map<String, Any>>()
                    var key = ""
                    if (list != null) {
                        for ((k, v) in list) {
                            key = k
                        }
                    }
                    if (key != "") {
                        profileInfo.firebasetoken = _auth.uid
                        if (profileInfo.profileImg != "") {
                            _storageRepository.uploadFile("UserImage/$key/profileImage", profileInfo.profileImg!!)
                                .addOnCompleteListener {
                                    _storageRepository.getDownloadUrl("UserImage/$key/profileImage")
                                        .addOnCompleteListener {
                                            profileInfo.profileImg = it.result.toString()

                                            val postValue = profileInfo.toMap()
                                            val childUpdate = hashMapOf<String, Any>(
                                                "/users/$key" to postValue
                                            )
                                            _dbRef.updateChildren(childUpdate)

                                            listener.OnComplete()
                                        }
                                }
                        } else {
                            val postValue = profileInfo.toMap()
                            val childUpdate = hashMapOf<String, Any>(
                                "/users/$key" to postValue
                            )
                            _dbRef.updateChildren(childUpdate)

                            listener.OnComplete()
                        }
                    }

                }
            })

    }

    fun getProfile(listener: ValueEventListener) {
        Log.d("BaseActivity", "uid : " + _auth.uid)
        _dbRef.child("users").orderByChild("firebasetoken").equalTo(_auth.uid).limitToFirst(1).addListenerForSingleValueEvent(listener)
    }
}