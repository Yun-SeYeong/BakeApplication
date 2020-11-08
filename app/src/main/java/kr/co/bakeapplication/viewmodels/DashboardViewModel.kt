package kr.co.bakeapplication.viewmodels

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kr.co.bakeapplication.data.Recipe
import kr.co.bakeapplication.repositorys.FirebaseAuthRepository
import kr.co.bakeapplication.repositorys.FirebaseDBRepository
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

    private val _firebaseDBRepository: FirebaseDBRepository by lazy {
        FirebaseDBRepository()
    }

    private val _isLogin: MutableLiveData<Boolean> by lazy {
        _currentState = DashboardState.startCheckLogin()
        val s = _firebaseAuthRepository.isLogin()
        _currentState = DashboardState.endCheckLogin()
        MutableLiveData<Boolean>(s)
    }

    val isLogin: LiveData<Boolean>
        get() = _isLogin

    private val _recipeList: ObservableArrayList<Recipe> by lazy {
        _currentState = DashboardState.startLoading()
        syncRecipes()
        _currentState = DashboardState.endLoading()
        ObservableArrayList<Recipe>()
    }

    val recipeList: ObservableArrayList<Recipe>
        get() = _recipeList

    fun userProfileButtonEvent() {
        Log.d("BaseActivity", "userProfileButtonEvent")
        val intent = Intent(application, ProfileActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(intent)
    }

    fun syncRecipes() {
        _currentState = DashboardState.startLoading()
        _firebaseDBRepository.readRecipes(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("BaseActivity", "onDataChange")
                val list = snapshot.getValue<Map<String, Any>>()
                Log.d("BaseActivity", "list :" + list)
                //Reset Recipe List
                val it = _recipeList.iterator()
                while(it.hasNext()){
                    it.apply {
                        next()
                        remove()
                    }
                }
                if (list != null) {
                    for ((k, v) in list) {
                        Log.d("BaseActivity", "value : " + v)
                        val r = Recipe("","", "",null)
                        r.toObj(v as Map<String, Any?>)
                        _recipeList.add(r)
                    }
                }
            }
        })
        _currentState = DashboardState.endLoading()
    }

    override fun onCleared() {
        super.onCleared()
    }
}