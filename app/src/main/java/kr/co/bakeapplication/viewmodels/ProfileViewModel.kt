package kr.co.bakeapplication.viewmodels

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kr.co.bakeapplication.R
import kr.co.bakeapplication.data.Profile
import kr.co.bakeapplication.repositorys.FirebaseDBRepository
import kr.co.bakeapplication.viewhandlers.AddRecipePageActivityHandler
import kr.co.bakeapplication.viewhandlers.BaseActivityHandler
import kr.co.bakeapplication.viewhandlers.ProfileActivityHandler
import kr.co.bakeapplication.views.CreateProfileActivity
import kr.co.bakeapplication.views.DashboardActivity
import kr.co.bakeapplication.views.MyRecipeActivity
import kr.co.bakeapplication.views.UpdateProfileActivity
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class ProfileViewModel(private val application: Activity): ViewModel() {
    private var _currentState: ProfileState by Delegates.observable(ProfileState.endLoading(), {
            _: KProperty<*>,
            _: ProfileState,
            newState: ProfileState ->
        when(newState){
            is ProfileState.init ->
                (application as BaseActivityHandler).endLoading()
            is ProfileState.startLoading ->
                (application as BaseActivityHandler).startLoading()
            is ProfileState.endLoading ->
                (application as BaseActivityHandler).endLoading()
            is ProfileState.startCheckProfile ->
                (application as ProfileActivityHandler).startCheckProfile()
            is ProfileState.endCheckProfile ->
                (application as ProfileActivityHandler).endCheckProfile(newState.profile)
            is ProfileState.onError ->
                (application as BaseActivityHandler).onError(newState.throwable)
        }
    })

    private val _firebaseDBRepository: FirebaseDBRepository by lazy {
        FirebaseDBRepository()
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

    private val isProfileExist = ObservableField<Boolean>(false)

    private val currentProfile = ObservableField<Profile?>()

    fun backButtonEvent() {
        Log.d("BaseActivity", "backButtonEvent")
        _startDashboardActivity()
    }

    fun myProfileButtonEvent() {
        if (!isProfileExist.get()!!) {
            _startCreateProfileActivity()
        }else{
            if (currentProfile.get() != null){
                _startUpdateProfileActivity(currentProfile.get()!!)
            }
        }
    }

    fun checkProfile() {
        _currentState = ProfileState.startCheckProfile()

        _firebaseDBRepository.getProfile(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                _currentState = ProfileState.endCheckProfile(null)
                isProfileExist.set(false)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val list = snapshot.getValue<Map<String, Any>>()
                Log.d("BaseActivity", "profile list: " + list)
                if (list != null) {
                    var p = Profile("", "", "")
                    for ((k, v) in list) {
                        p.toObj(v as Map<String, Any?>)
                    }
                    if (p.firebasetoken != "") {
                        isProfileExist.set(true)
                        _currentState = ProfileState.endCheckProfile(p)
                        currentProfile.set(p)
                    } else {
                        isProfileExist.set(false)
                        _currentState = ProfileState.endCheckProfile(null)
                    }
                }
            }
        })
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

    private fun _startCreateProfileActivity() {
        val intent = Intent(application, CreateProfileActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(intent)
    }

    private fun _startUpdateProfileActivity(profile: Profile) {
        val intent = Intent(application, UpdateProfileActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("Profile", profile)
        application.startActivity(intent)
    }

    private fun _logout() {
        Firebase.auth.signOut()
        _mGoogleSignInClient.signOut()
    }
}