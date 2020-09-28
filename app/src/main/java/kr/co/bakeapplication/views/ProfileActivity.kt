package kr.co.bakeapplication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import kr.co.bakeapplication.R
import kr.co.bakeapplication.databinding.ActivityProfileBinding
import kr.co.bakeapplication.viewmodels.ProfileViewModel

class ProfileActivity : AppCompatActivity() {
    private val mBinding: ActivityProfileBinding by lazy { DataBindingUtil.setContentView<ActivityProfileBinding>(this, R.layout.activity_profile) }
    private val mViewModel: ProfileViewModel by lazy { ProfileViewModel(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mBinding.viewmodel = mViewModel
    }
}
