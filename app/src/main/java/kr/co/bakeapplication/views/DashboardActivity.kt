package kr.co.bakeapplication.views

import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kr.co.bakeapplication.R
import kr.co.bakeapplication.data.Profile
import kr.co.bakeapplication.databinding.ActivityDashboardBinding
import kr.co.bakeapplication.repositorys.FirebaseAuthFactory
import kr.co.bakeapplication.viewhandlers.DashboardActivityHandler
import kr.co.bakeapplication.viewmodels.DashboardViewModel

class DashboardActivity : BaseActivity(), DashboardActivityHandler {
    private val TAG = "DashboardActivity"
    private val mBinding: ActivityDashboardBinding by lazy { DataBindingUtil.setContentView<ActivityDashboardBinding>(this, R.layout.activity_dashboard) }
    private val mViewModel: DashboardViewModel by lazy {
        ViewModelProvider(this, FirebaseAuthFactory(this))
            .get(DashboardViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        //AAC ViewModel 생성
        mBinding.lifecycleOwner = this
        mBinding.viewmodel = mViewModel

        //Sync Profile
        mViewModel.syncProfile()

        //SwipeRefreshLayout
        mBinding.swiperefreshlayoutDashboardMainboard.setOnRefreshListener {
            mViewModel.syncRecipes()
            endLoading()
        }

        mViewModel.isLogin.observe(this, Observer {
            Log.d(TAG, "isLogin : $it")
            if (!it) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        })
    }

    override fun startSyncProfile() {
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun endSyncProfile(profile: Profile?) {
        Log.d("BaseActivity", "profileimg" + profile!!.profileImg)
        if (profile != null && profile!!.profileImg != "") {
            mBinding.imageviewDashboardProfile.background = ShapeDrawable(OvalShape())
            mBinding.imageviewDashboardProfile.clipToOutline = true
            Glide.with(this).load(profile!!.profileImg).into(mBinding.imageviewDashboardProfile)
        }
    }

    override fun startLoading() {
        super.startLoading()
    }

    override fun endLoading() {
        super.endLoading()
        mBinding.swiperefreshlayoutDashboardMainboard.isRefreshing = false
    }

    fun onProfileClicked(view: View) {
        mViewModel.userProfileButtonEvent()
    }
}
