package kr.co.bakeapplication.viewmodels

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kr.co.bakeapplication.data.Recipe
import kr.co.bakeapplication.repositorys.FirebaseDBRepository
import kr.co.bakeapplication.viewhandlers.BaseActivityHandler
import kr.co.bakeapplication.views.DashboardActivity
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class MyRecipeViewModel(private val application: Activity): ViewModel() {
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

    private val _firebaseDBRepository: FirebaseDBRepository by lazy {
        FirebaseDBRepository()
    }

    private val _recipeList: ObservableArrayList<Recipe> by lazy {
        _currentState = MyRecipeState.startLoading()
        syncRecipes()
        _currentState = MyRecipeState.endLoading()
        ObservableArrayList<Recipe>()
    }

    val recipeList: ObservableArrayList<Recipe>
        get() = _recipeList


    fun addRecipe(recipe: Recipe) {
        _firebaseDBRepository.writeRecipe(recipe)
    }

    fun syncRecipes() {
        _currentState = MyRecipeState.startLoading()
        _firebaseDBRepository.readRecipes(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("BaseActivity", "onDataChange")
                val list = snapshot.getValue<Map<String, Any>>()
                Log.d("BaseActivity", "list :" + list)
                if (list != null) {
                    for ((k, v) in list) {
                        Log.d("BaseActivity", "value : " + v)
                        val r = Recipe("","", null)
                        r.toObj(v as Map<String, Any?>)
                        _recipeList.add(r)
                    }
                }
            }
        })
        _currentState = MyRecipeState.endLoading()
    }

    fun backButtonEvent() {
        val intent = Intent(application, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(intent)
    }
}