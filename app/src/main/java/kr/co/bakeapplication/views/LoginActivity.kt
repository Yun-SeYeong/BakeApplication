package kr.co.bakeapplication.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.co.bakeapplication.R
import kr.co.bakeapplication.databinding.ActivityLoginBinding
import kr.co.bakeapplication.repositorys.FirebaseAuthFactory
import kr.co.bakeapplication.viewmodels.LoginViewModel

class LoginActivity : BaseActivity() {
    private val TAG = "LoginActivity"
    private val mBinding: ActivityLoginBinding by lazy { DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login) }
    private val mViewModel: LoginViewModel by lazy {
        ViewModelProvider(this, FirebaseAuthFactory(this))
            .get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //AAC ViewModel 생성
        mBinding.lifecycleOwner = this
        mBinding.viewmodel = mViewModel

        mViewModel.signIn()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                Log.d("BaseActivity", "onActivityREsult")
                val account = task.getResult(ApiException::class.java)!!
                mViewModel.signInResult(account.idToken!!)
            } catch (e: ApiException) {
                super.onError(e)
            }
        }
    }

    override fun endLoading() {
        super.endLoading()
        Log.d("BaseActivity", "ssisLogin ${Firebase.auth.currentUser != null}")
        if (Firebase.auth.currentUser != null) {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

}
