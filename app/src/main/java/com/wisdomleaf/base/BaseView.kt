package com.wisdomleaf.base

import android.app.Activity

interface BaseView {

    fun viewActivity(): Activity

    fun showProgress()

    fun hideProgress()

    fun showToast(message: String)

    fun showError(message: String)

    fun onNetworkStateChange(isConnect: Boolean)

    fun hideStatusBar()
}
