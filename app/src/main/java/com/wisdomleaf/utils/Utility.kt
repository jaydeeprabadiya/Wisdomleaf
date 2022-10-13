package com.wisdomleaf.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.wisdomleaf.R
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object Utility {
    var STATUS_HEIGHT: Int? = 0

    /* private val PASSWORD_PATTERN =
         Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")*/
    private val PASSWORD_PATTERN =
        Pattern.compile("^(?=.*?[a-zA-Z])(?=.*?[0-9]).{6,}$")
    private val TAG = Utility::class.java.simpleName
    private var snackbar: Snackbar? = null
    private var progressDialog: Dialog? = null
    private val cache = Hashtable<String, Typeface>()
    private val dialog: Dialog? = null

    val SERVER_FORMAT: String = "yyyy-MM-dd'T'HH:mm:ssZ"
    internal var REGULAR_FORMAT = "dd-MM-yyyy HH:mm"



    fun printHashKey(pContext: Context) {
        try {
            val info = pContext.packageManager.getPackageInfo(
                pContext.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Logger.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi / 160f)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */


    fun hideKeyboard(aContext: Activity?) {
        if (aContext != null) {
            val im =
                aContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(
                aContext.window.decorView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    fun showKeyboard(aEditText: EditText, aContext: AppCompatActivity?) {
        if (aContext != null) {
            val im =
                aContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.showSoftInput(aEditText, InputMethodManager.SHOW_FORCED)
        }
    }

    /**
     * Method Name: hideProgressDialog Created By: dev458 Created Date:
     * 28/March/2013 Modified By: Modified Date: Purpose: This method is used to
     * hide progress dialog and destroy progress dialog instance .
     */
    fun hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        } catch (ignored: Throwable) {

        } finally {
            progressDialog = null
        }
    }

    /**
     * This method is used to show progress indicator dialog with message when
     * web service is called.
     */
    fun showProgressDialog(context: Context?) {

        if (context != null && !(context as AppCompatActivity).isFinishing) {
            if (progressDialog == null || !progressDialog!!.isShowing) {
                progressDialog = Dialog(context)
                progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                try {
                    val dividerId = progressDialog!!.context.resources.getIdentifier(
                        "android:id/titleDivider",
                        null,
                        null
                    )
                    val divider = progressDialog!!.findViewById<View>(dividerId)
                    divider?.setBackgroundColor(context.resources.getColor(android.R.color.transparent))
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                progressDialog!!.setContentView(R.layout.custom_progressbar)
                progressDialog!!.setCancelable(false)
                if (!context.isFinishing)
                    progressDialog!!.show()
            }
        } else {
            assert(context != null)
            Logger.e(TAG, context!!.toString() + " Context Null")
        }
    }



    fun validateContext(context: Context?): Boolean {
        return context != null && !(context as Activity).isFinishing
    }



    fun hasConnection(context: Context): Boolean {
        if (ConnectivityReceiver.isNetworkConnected(context))
            return true
        else {
            val alertDialog = android.app.AlertDialog.Builder(context).create()
            alertDialog.setTitle(context.getString(R.string.app_name))
            alertDialog.setMessage(context.getString(R.string.no_internet_message))
            alertDialog.setButton(
                DialogInterface.BUTTON_POSITIVE, "Ok"
            ) { dialogInterface, i -> alertDialog.dismiss() }

            if (!alertDialog.isShowing) {
                alertDialog.show()
            }
            return false
        }
    }



    fun getFormat(format: String): SimpleDateFormat {
        val dateFormat = SimpleDateFormat(format, Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat
    }

    fun getDefaultFormat(format: String): SimpleDateFormat {
        val dateFormat = SimpleDateFormat(format, Locale.US)
        dateFormat.timeZone = TimeZone.getDefault()
        return dateFormat
    }



    private fun addPermission(
        activity: Activity,
        permissionsList: MutableList<String>,
        permission: String
    ): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activity,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsList.add(permission)
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                return false
        }
        return true
    }


    fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }


}