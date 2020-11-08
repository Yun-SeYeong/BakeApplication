package kr.co.bakeapplication.views

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import kr.co.bakeapplication.R
import kr.co.bakeapplication.data.Profile
import kr.co.bakeapplication.databinding.ActivityProfileBinding
import kr.co.bakeapplication.viewhandlers.ProfileActivityHandler
import kr.co.bakeapplication.viewmodels.ProfileViewModel

class ProfileActivity : BaseActivity(), ProfileActivityHandler {
    private val mBinding: ActivityProfileBinding by lazy { DataBindingUtil.setContentView<ActivityProfileBinding>(this, R.layout.activity_profile) }
    private val mViewModel: ProfileViewModel by lazy { ProfileViewModel(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mBinding.viewmodel = mViewModel
        mViewModel.checkProfile()
    }

    override fun startCheckProfile() {
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun endCheckProfile(profile: Profile?) {
        if (profile != null) {
            mBinding.textviewProfileUsername.text = profile.profilename
            mBinding.lineProfileLine2.visibility = View.VISIBLE
            mBinding.layoutProfileProfileinfo.visibility = View.VISIBLE
            mBinding.buttonProfileMyprofile.text = "프로필 수정"
            if (profile.profileImg != "") {
                mBinding.imageviewProfileProfileImg.background = ShapeDrawable(OvalShape())
                mBinding.imageviewProfileProfileImg.clipToOutline = true
                Glide.with(this).load(profile.profileImg).into(mBinding.imageviewProfileProfileImg)
            }
        } else {
            mBinding.lineProfileLine2.visibility = View.GONE
            mBinding.layoutProfileProfileinfo.visibility = View.GONE
            mBinding.buttonProfileMyprofile.text = "프로필 등록"
        }
    }
}
