package com.wisdomleaf.utils


import android.util.Log
import com.intuit.sdp.BuildConfig

class Logger {
    companion object {
        fun d(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.d(tag, message)
            }
        }

        fun e(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.e(tag, message)
            }
        }
    }
}