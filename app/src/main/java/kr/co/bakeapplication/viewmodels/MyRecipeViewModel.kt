package kr.co.bakeapplication.viewmodels

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.bakeapplication.data.Recipe

class MyRecipeViewModel: ViewModel() {
    private val _recipeList: ObservableArrayList<Recipe> by lazy {
        ObservableArrayList<Recipe>()
    }

    val recipeList: ObservableArrayList<Recipe>
        get() = _recipeList


    fun addRecipe(recipe: Recipe) {
        _recipeList.add(recipe)
    }
}