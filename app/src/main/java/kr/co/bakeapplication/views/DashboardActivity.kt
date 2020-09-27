package kr.co.bakeapplication.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.co.bakeapplication.R
import kr.co.bakeapplication.databinding.ActivityDashboardBinding
import kr.co.bakeapplication.repositorys.FirebaseAuthFactory
import kr.co.bakeapplication.viewmodels.DashboardViewModel

class DashboardActivity : BaseActivity() {
    private val TAG = "DashboardActivity"
    lateinit var mBinding : ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        Log.d(TAG, "onCreate")

        //AAC ViewModel 생성
        val viewModel = ViewModelProvider(this, FirebaseAuthFactory(this))
            .get(DashboardViewModel::class.java)

        mBinding.viewmodel = viewModel

        viewModel.isLogin.observe(this, Observer {
            Log.d(TAG, "isLogin : $it")
            if (!it) {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        })
    }
}
