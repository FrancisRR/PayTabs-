package com.sdk.paytab

import android.widget.Toast
import com.sdk.paytab.base.AppController

object UiUtils {


    internal fun showToast(message: String?) {
        Toast.makeText(AppController.instance, "$message", Toast.LENGTH_SHORT).show()
    }

}