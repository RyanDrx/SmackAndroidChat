package com.ryandrx.smack.Controller

import android.app.Application
import com.ryandrx.smack.Utilities.SharedPrefs

/**
 * Created by RyanDrx on 10/9/2017.
 */
class App : Application() {

    companion object {
        lateinit var prefs: SharedPrefs
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }

}