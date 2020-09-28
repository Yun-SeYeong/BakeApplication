package kr.co.bakeapplication.viewmodels

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.bakeapplication.repositorys.FirebaseAuthRepository
import kr.co.bakeapplication.viewhandlers.BaseActivityHandler
import kr.co.bakeapplication.views.ProfileActivity
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class DashboardViewModel(private val application: Activity): ViewModel() {
    private var _currentState: DashboardState by Delegates.observable(DashboardState.endCheckLogin(), {
        _: KProperty<*>,
        _: DashboardState,
        newState: DashboardState ->
        when(newState){
            is DashboardState.init ->
                (application as BaseActivityHandler).endLoading()
            is DashboardState.startCheckLogin ->
                (application as BaseActivityHandler).startLoading()
            is DashboardState.endCheckLogin ->
                (application as BaseActivityHandler).endLoading()
            is DashboardState.onError ->
                (application as BaseActivityHandler).onError(newState.throwable)
        }
    })
    private val _firebaseAuthRepository: FirebaseAuthRepository by lazy {
        FirebaseAuthRepository()
    }
    private val _isLogin: MutableLiveData<Boolean> by lazy {
        _currentState = DashboardState.startCheckLogin()
        val s = _firebaseAuthRepository.isLogin()
        _currentState = DashboardState.endCheckLogin()
        MutableLiveData<Boolean>(s)
    }

    val isLogin: LiveData<Boolean>
        get() = _isLogin

    fun userProfileButtonEvent() {
        Log.d("BaseActivity", "userProfileButtonEvent")
        val intent = Intent(application, ProfileActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(intent)
    }


    override fun onCleared() {
        super.onCleared()
    }
}