package com.wisdomleaf.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.wisdomleaf.R
import com.wisdomleaf.utils.Logger
import com.wisdomleaf.utils.Utility


abstract class BaseActivity : AppCompatActivity(), BaseView {

    lateinit var receiver: BroadcastReceiver
    private var intentFilter: IntentFilter? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                onNetworkStateChange(isNetworkConnected(this@BaseActivity))
                Logger.e("status", isNetworkConnected(this@BaseActivity).toString())
            }
        }
        intentFilter = IntentFilter()
        intentFilter?.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.anim.fade_out)
    }

    override fun onResume() {
        this.registerReceiver(receiver, intentFilter)
        super.onResume()
    }

    override fun showProgress() {
        Utility.showProgressDialog(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        hideProgress()
    }

    override fun onPause() {
        this.unregisterReceiver(receiver)
        super.onPause()
    }

    override fun hideProgress() {
        Utility.hideProgressDialog()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun hideStatusBar(){
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    fun showSnackBar(view: View, message: String, time: Int, isTypeError: Boolean) {
        val snackbar = Snackbar.make(view, message, time)
        val snackBarView = snackbar.view

        if (isTypeError)
            snackBarView.setBackgroundColor(Color.parseColor("#E41200"))
        else
            snackBarView.setBackgroundColor(Color.parseColor("#34A853"))

        snackbar.show()
    }

    companion object {

        fun isNetworkConnected(context: Context): Boolean {
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                val networks: Array<Network> = cm.allNetworks
                var hasInternet = false

                if (networks.isNotEmpty()) {
                    for (network: Network in networks) {
                        val nc = cm.getNetworkCapabilities(network)
                        if (nc?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)!!) {
                            hasInternet = true
                        }
                    }
                }
                hasInternet
            } else {
                val activeNetwork = cm.activeNetworkInfo
                activeNetwork != null && activeNetwork.isConnectedOrConnecting
            }
        }
    }
}
