package kr.co.bakeapplication.repositorys

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.bakeapplication.viewmodels.DashboardViewModel
import kr.co.bakeapplication.viewmodels.LoginViewModel

class FirebaseAuthFactory(private val activity: Activity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DashboardViewModel::class.java)){
            DashboardViewModel(activity) as T
        }else if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            LoginViewModel(activity) as T
        }else{
            throw IllegalArgumentException()
        }
    }
}