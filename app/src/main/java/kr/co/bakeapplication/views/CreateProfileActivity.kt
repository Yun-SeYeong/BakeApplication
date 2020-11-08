package kr.co.bakeapplication.views

import android.app.Activity
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kr.co.bakeapplication.R
import kr.co.bakeapplication.databinding.ActivityCreateProfileBinding
import kr.co.bakeapplication.viewmodels.CreateProfileViewModel

class CreateProfileActivity : BaseActivity() {
    val mBinding: ActivityCreateProfileBinding by lazy { DataBindingUtil.setContentView<ActivityCreateProfileBinding>(this,
        R.layout.activity_create_profile
    )}
    val mViewModel: CreateProfileViewModel by lazy { CreateProfileViewModel(this) }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.viewmodel = mViewModel

        mViewModel.profileImg.observe(this, Observer {
            if (it != null) {
                mBinding.imageviewCreateprofileProfileimg.setImageURI(it)
                mBinding.imageviewCreateprofileProfileimg.background = ShapeDrawable(OvalShape())
                mBinding.imageviewCreateprofileProfileimg.clipToOutline = true
            }

        })
    }
}