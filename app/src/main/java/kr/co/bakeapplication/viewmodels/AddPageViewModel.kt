package kr.co.bakeapplication.viewmodels

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.dhaval2404.imagepicker.ImagePicker

class AddPageViewModel(private val application: Activity): ViewModel(){
    val _pageImage = MutableLiveData<Uri>(null)
    val pageImage: LiveData<Uri> by lazy {
        _pageImage
    }

    fun loadImagePicker() {
        ImagePicker.with(application)
            .compress(1024)         //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
            .start { resultCode, data ->
                if (resultCode == Activity.RESULT_OK) {
                    Log.d("BaseActivity", "data : " + data?.data)
                    _pageImage.postValue(data?.data)
                }
            }
    }
}