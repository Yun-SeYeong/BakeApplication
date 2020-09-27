package kr.co.bakeapplication.repositorys

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthRepository {
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }


    fun isLogin(): Boolean{
        return auth.currentUser != null
    }

    fun signIn(email: String, password: String): Task<AuthResult> {
        Log.d("LoginActivity", "signIn() [email=$email , password=$password]")
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun signInWithGoogle(idToken: String): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential)
    }

}