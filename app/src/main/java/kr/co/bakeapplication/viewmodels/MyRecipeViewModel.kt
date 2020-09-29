package kr.co.bakeapplication.viewmodels

import android.app.Activity
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.bakeapplication.data.Recipe
import kr.co.bakeapplication.repositorys.FirebaseDBFactory
import kr.co.bakeapplication.viewhandlers.BaseActivityHandler
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class MyRecipeViewModel(application: Activity): ViewModel() {
    private var _currentState: MyRecipeState by Delegates.observable(MyRecipeState.endLoading(), {
            _: KProperty<*>,
            _: MyRecipeState,
            newState: MyRecipeState ->
        when(newState){
            is MyRecipeState.init ->
                (application as BaseActivityHandler).endLoading()
            is MyRecipeState.startLoading ->
                (application as BaseActivityHandler).startLoading()
            is MyRecipeState.endLoading ->
                (application as BaseActivityHandler).endLoading()
            is MyRecipeState.onError ->
                (application as BaseActivityHandler).onError(newState.throwable)
        }
    })

    private val _firebaseDBRepository: FirebaseDBFactory by lazy {
        FirebaseDBFactory()
    }

    private val _recipeList: ObservableArrayList<Recipe> by lazy {
        _currentState = MyRecipeState.startLoading()
        _currentState = MyRecipeState.endLoading()
        ObservableArrayList<Recipe>()
    }

    val recipeList: ObservableArrayList<Recipe>
        get() = _recipeList


    fun addRecipe(recipe: Recipe) {
        _firebaseDBRepository.writeRecipe(recipe)
    }
}