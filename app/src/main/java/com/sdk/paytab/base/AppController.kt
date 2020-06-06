package com.sdk.paytab.base

import android.app.Application

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        instance=this
    }

    companion object{
      internal lateinit var instance:AppController
    }
}