package com.wisdomleaf.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.math.BigInteger
import java.net.InetAddress


class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, arg1: Intent) {
        val isConnected = isNetworkConnected(context)
        val networkStateIntent = Intent(NETWORK_AVAILABLE_ACTION)
        networkStateIntent.putExtra(IS_NETWORK_AVAILABLE, isConnected)
        LocalBroadcastManager.getInstance(context).sendBroadcast(networkStateIntent)

    }

    companion object {
        val NETWORK_AVAILABLE_ACTION = "Connectivity Receiver"
        val IS_NETWORK_AVAILABLE = "isNetworkAvailable"

        var TYPE_WIFI = 1
        var TYPE_MOBILE = 2
        var TYPE_NOT_CONNECTED = 0

        fun isNetworkConnected(context: Context?): Boolean {
            if (context != null) {
                val cm = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                val activeNetwork = cm.activeNetworkInfo
                return activeNetwork != null && activeNetwork.isConnectedOrConnecting
            } else
                return false
        }

        fun getConnectivityStatus(context: Context): Int {
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo
            if (null != activeNetwork) {
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI

                if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE
            }
            return TYPE_NOT_CONNECTED
        }

        fun getConnectivityStatusString(context: Context): String? {
            val conn = getConnectivityStatus(context)
            var status: String? = null
            if (conn == TYPE_WIFI) {
                status = "Wifi enabled"
            } else if (conn == TYPE_MOBILE) {
                status = "Mobile data enabled"
            } else if (conn == TYPE_NOT_CONNECTED) {
                status = "Not connected to Internet"
            }
            return status
        }

        fun isConnected(context: Context): Boolean {
            val cm =
                context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

        fun getIp(context: Context): String {
            val wm: WifiManager = context.getSystemService(WIFI_SERVICE) as WifiManager
            val ip = wm.connectionInfo.ipAddress
            val ipAddress = BigInteger.valueOf(ip.toLong()).toByteArray()
            val myaddr = InetAddress.getByAddress(ipAddress)
            return myaddr.getHostAddress()
        }
    }
}
