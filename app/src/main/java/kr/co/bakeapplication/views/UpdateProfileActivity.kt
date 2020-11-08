package kr.co.bakeapplication.views

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kr.co.bakeapplication.R
import kr.co.bakeapplication.data.Profile
import kr.co.bakeapplication.databinding.ActivityUpdateProfileBinding
import kr.co.bakeapplication.viewmodels.UpdateProfileViewModel

class UpdateProfileActivity : AppCompatActivity() {
    private val mBinding: ActivityUpdateProfileBinding by lazy { DataBindingUtil.setContentView<ActivityUpdateProfileBinding>(this, R.layout.activity_update_profile) }
    private val mViewModel: UpdateProfileViewModel by lazy { UpdateProfileViewModel(this) }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.viewmodel = mViewModel

        if (intent != null){
            val profileInfo = intent.getSerializableExtra("Profile") as Profile
            mViewModel.profileName.set(profileInfo.profilename)
            mBinding.imageviewUpdateprofileProfileimg.background = ShapeDrawable(OvalShape())
            mBinding.imageviewUpdateprofileProfileimg.clipToOutline = true
            Glide.with(this).load(profileInfo.profileImg).into(mBinding.imageviewUpdateprofileProfileimg)
        }

        mViewModel.profileImg.observe(this, Observer {
            if (it != null) {
                mBinding.imageviewUpdateprofileProfileimg.setImageURI(it)
                mBinding.imageviewUpdateprofileProfileimg.background = ShapeDrawable(OvalShape())
                mBinding.imageviewUpdateprofileProfileimg.clipToOutline = true
            }

        })
    }
}