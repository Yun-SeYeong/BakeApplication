package kr.co.bakeapplication.repositorys

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.co.bakeapplication.data.Recipe

class FirebaseDBRepository {

    private val _dbRef: DatabaseReference by lazy {
        Firebase.database.reference
    }

    private val _auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    fun writeRecipe(recipe: Recipe) {
        val key = _dbRef.child("recipes").push().key
        recipe.creatoruid = _auth.uid!!
        val postValue = recipe.toMap()

        val childUpdate = hashMapOf<String, Any>(
            "/recipes/$key" to postValue
        )
        _dbRef.updateChildren(childUpdate)
    }

    fun readRecipes(listener: ValueEventListener) {
        _dbRef.child("recipes").addListenerForSingleValueEvent(listener)
    }
}