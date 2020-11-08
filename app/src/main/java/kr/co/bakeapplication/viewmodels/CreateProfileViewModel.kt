package kr.co.bakeapplication.viewmodels

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import kr.co.bakeapplication.data.Profile
import kr.co.bakeapplication.repositorys.FirebaseDBRepository
import kr.co.bakeapplication.views.CreateProfileActivity
import kr.co.bakeapplication.views.DashboardActivity

class CreateProfileViewModel(private val application: Activity): ViewModel() {
    private val _profileImg = MutableLiveData<Uri>(null)
    val profileImg: LiveData<Uri> by lazy {
        _profileImg
    }

    val profileName = ObservableField<String>("")

    private val _firebaseDBRepository: FirebaseDBRepository by lazy {
        FirebaseDBRepository()
    }

    fun createProfileButtonEvent() {
        var profileImgPath = ""
        if (profileImg.value != null) {
            profileImgPath = profileImg.value!!.path!!
        }
        val profileInfo = Profile("", profileName.get(), profileImgPath)
        _firebaseDBRepository.createProfile(profileInfo, object: FirebaseDBRepository.OnWriteProfileListener {
            override fun OnComplete() {
                Log.d("BaseActivity", "Profile Created :" + profileInfo.profilename)
                _startDashboardActivity()
            }

        })
    }

    fun backButtonEvent() {
        Log.d("BaseActivity", "backButtonEvent")
        _startDashboardActivity()
    }

    fun profileImgButtonEvent() {
        ImagePicker.with(application)
            .compress(1024)         //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
            .start { resultCode, data ->
                if (resultCode == Activity.RESULT_OK) {
                    Log.d("BaseActivity", "data : " + data?.data)
                    _profileImg.postValue(data?.data)
                }
            }
    }

    private fun _startDashboardActivity() {
        val intent = Intent(application, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        application.startActivity(intent)
    }
}