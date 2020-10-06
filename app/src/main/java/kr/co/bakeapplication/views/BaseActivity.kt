package kr.co.bakeapplication.views

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kr.co.bakeapplication.R
import kr.co.bakeapplication.databinding.ActivityLoginBinding
import kr.co.bakeapplication.viewhandlers.BaseActivityHandler

abstract class BaseActivity : AppCompatActivity(), BaseActivityHandler {
    private val TAG = "BaseActivity"

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun startLoading() {
        Log.d(TAG, "startLoading()")
    }

    override fun endLoading() {
        Log.d(TAG, "endLoading()")
    }

    override fun onError(throwable: Throwable) {
        Log.d(TAG, "onError()")
    }

    override fun onBackPressed() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
    }
}