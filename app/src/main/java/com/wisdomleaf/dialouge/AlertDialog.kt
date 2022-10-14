package com.wisdomleaf.dialouge

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.wisdomleaf.R
import kotlinx.android.synthetic.main.dialog_alert.*

class AlertDialog(internal var context: Context, internal var listener : RegisterClickListener,var name:String): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));// *that how i made background transparent so that **Only** rounded border can be seen*
        setContentView(R.layout.dialog_alert)

        lblAgeRestriction.text=name

        txtYesIAm?.setOnClickListener{
            listener?.txtOkRegisterListener()
        }
    }

    interface RegisterClickListener{
        fun txtOkRegisterListener()
    }
}