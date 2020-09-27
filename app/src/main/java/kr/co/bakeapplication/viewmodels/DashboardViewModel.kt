package kr.co.bakeapplication.viewmodels

import android.app.Activity
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.bakeapplication.repositorys.FirebaseAuthRepository
import kr.co.bakeapplication.viewhandlers.BaseActivityHandler
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class DashboardViewModel(application: Activity): ViewModel() {
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
        _currentState = DashboardState.startCheckLogin()
        MutableLiveData<Boolean>(s)
    }

    val isLogin: LiveData<Boolean>
        get() = _isLogin


    override fun onCleared() {
        super.onCleared()
    }
}