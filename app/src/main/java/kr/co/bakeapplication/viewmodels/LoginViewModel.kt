package kr.co.bakeapplication.viewmodels

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.co.bakeapplication.R
import kr.co.bakeapplication.repositorys.FirebaseAuthRepository
import kr.co.bakeapplication.viewhandlers.BaseActivityHandler
import kotlin.math.sign
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class LoginViewModel(private val application: Activity): ViewModel() {
    private var _currentState: LoginState by Delegates.observable(LoginState.init(),{
        _: KProperty<*>,
        _: LoginState,
        newState: LoginState ->
        when(newState) {
            is LoginState.init ->
                (application as BaseActivityHandler).endLoading()
            is LoginState.startLogin ->
                (application as BaseActivityHandler).startLoading()
            is LoginState.endLogin ->
                (application as BaseActivityHandler).endLoading()
            is LoginState.onError ->
                (application as BaseActivityHandler).onError(newState.throwable)
        }
    })

    private val _firebaseAuthRepository: FirebaseAuthRepository by lazy {
        FirebaseAuthRepository()
    }

    private val _gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val _mGoogleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(application, _gso)
    }

    val userId = ObservableField<String>("")
    val password = ObservableField<String>("")

    fun onSubmit() {
        _currentState = LoginState.startLogin()
        Log.d("LoginActivity", "userid : ${userId.get()}")
        Log.d("LoginActivity", "password : ${password.get()}")
        _firebaseAuthRepository.signIn(userId.get()!!, password.get()!!)
            .addOnCompleteListener(application) { task ->
                _currentState = if (task.isSuccessful){
                    LoginState.endLogin()
                }else{
                    LoginState.onError(task.exception!!)
                }
            }
    }

    fun signIn() {
        val signInIntent = _mGoogleSignInClient.signInIntent
        application.startActivityForResult(signInIntent, 100)
    }

    fun signInResult(idToken: String) {
        _currentState = LoginState.startLogin()
        Log.d("BaseActivity", "signInResult")
        _firebaseAuthRepository.signInWithGoogle(idToken)
            .addOnCompleteListener(application) { task ->
                _currentState = if (task.isSuccessful) {
                    LoginState.endLogin()
                } else {
                    LoginState.onError(task.exception!!)
                }
            }
    }
}