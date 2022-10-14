package com.wisdomleaf.mvvm.view.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.wisdomleaf.R
import com.wisdomleaf.base.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {
    private var TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            startActivity(Intent(this@SplashActivity, ListActivity::class.java))
            finish()
        }

    }

    override fun viewActivity(): Activity {
        return this
    }

    override fun onNetworkStateChange(isConnect: Boolean) {

    }
}