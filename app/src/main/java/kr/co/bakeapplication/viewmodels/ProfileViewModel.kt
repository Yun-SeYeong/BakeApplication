package kr.co.bakeapplication.viewmodels

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.co.bakeapplication.R
import kr.co.bakeapplication.views.DashboardActivity
import kr.co.bakeapplication.views.MyRecipeActivity

class ProfileViewModel(private val application: Activity): ViewModel() {
    private val _gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val _mGoogleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(application, _gso)
    }

    fun backButtonEvent() {
        Log.d("BaseActivity", "backButtonEvent")
        _startDashboardActivity()
    }

    fun myProfileButtonEvent() {

    }

    fun myReceipeButtonEvent() {
        val intent = Intent(application, MyRecipeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(intent)
    }

    fun logoutButtonEvent() {
        Log.d("BaseActivity", "logoutButtonEvent")
        _logout()
        _startDashboardActivity()
    }

    private fun _startDashboardActivity() {
        val intent = Intent(application, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(intent)
    }

    private fun _logout() {
        Firebase.auth.signOut()
        _mGoogleSignInClient.signOut()
    }
}