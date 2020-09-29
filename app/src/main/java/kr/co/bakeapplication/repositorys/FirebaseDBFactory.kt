package kr.co.bakeapplication.repositorys

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.co.bakeapplication.data.Recipe

class FirebaseDBFactory {

    private val _dbRef: DatabaseReference by lazy {
        Firebase.database.reference
    }

    fun writeRecipe(recipe: Recipe) {
        _dbRef.setValue(recipe)
    }
}