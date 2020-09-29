package kr.co.bakeapplication.repositorys

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.bakeapplication.viewmodels.DashboardViewModel
import kr.co.bakeapplication.viewmodels.LoginViewModel
import kr.co.bakeapplication.viewmodels.MyRecipeViewModel

class RecipeViewModelFactory(private val activity: Activity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MyRecipeViewModel::class.java)){
            MyRecipeViewModel(activity) as T
        }else{
            throw IllegalArgumentException()
        }
    }
}
