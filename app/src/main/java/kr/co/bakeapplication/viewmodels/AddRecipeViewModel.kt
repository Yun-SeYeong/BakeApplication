package kr.co.bakeapplication.viewmodels

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import kr.co.bakeapplication.data.Recipe
import kr.co.bakeapplication.data.RecipePage
import kr.co.bakeapplication.repositorys.FirebaseDBRepository
import kr.co.bakeapplication.viewhandlers.AddRecipePageActivityHandler
import kr.co.bakeapplication.viewhandlers.BaseActivityHandler
import kr.co.bakeapplication.views.MyRecipeActivity
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class AddRecipeViewModel(private val application: Activity): ViewModel() {
    private var _currentState: AddRecipePageState by Delegates.observable(AddRecipePageState.endLoading(), {
            _: KProperty<*>,
            _: AddRecipePageState,
            newState: AddRecipePageState ->
        when(newState){
            is AddRecipePageState.init ->
                (application as BaseActivityHandler).endLoading()
            is AddRecipePageState.startLoading ->
                (application as BaseActivityHandler).startLoading()
            is AddRecipePageState.endLoading ->
                (application as BaseActivityHandler).endLoading()
            is AddRecipePageState.refreshList ->
                (application as AddRecipePageActivityHandler).refreshList()
            is AddRecipePageState.onError ->
                (application as BaseActivityHandler).onError(newState.throwable)
        }
    })

    private val _firebaseDBRepository: FirebaseDBRepository by lazy {
        FirebaseDBRepository()
    }

    private val _recipePageList: ObservableArrayList<RecipePage> by lazy {
        ObservableArrayList<RecipePage>()
    }

    val title = ObservableField<String>("")

    private val _thumbNailImage = MutableLiveData<Uri>(null)
    val thumbNailImage: LiveData<Uri> by lazy {
        _thumbNailImage
    }

    val recipePageList: ObservableArrayList<RecipePage>
        get() = _recipePageList

    fun addPageButtonEvent() {
        _currentState = AddRecipePageState.refreshList()
        _currentState = AddRecipePageState.startLoading()
        _recipePageList.add(RecipePage("","",""))
        _currentState = AddRecipePageState.endLoading()
    }

    fun saveButtonEvent() {
        _currentState = AddRecipePageState.startLoading()
        _firebaseDBRepository.writeRecipe(Recipe(title.get()!!, "",thumbNailImage.value!!.path!!, _recipePageList), object: FirebaseDBRepository.OnWriteRecipeListener{
            override fun OnComplete() {
                _currentState = AddRecipePageState.endLoading()
                val intent = Intent(application, MyRecipeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                application.startActivity(intent)
            }
        })
    }

    fun loadImagePicker() {
        ImagePicker.with(application)
            .compress(1024)         //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
            .start { resultCode, data ->
                if (resultCode == Activity.RESULT_OK) {
                    Log.d("BaseActivity", "data : " + data?.data)
                    _thumbNailImage.postValue(data?.data)
                }
            }
    }

}