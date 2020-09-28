package com.dench.mjtest.base

import android.app.Application
import com.dench.mjtest.utils.AppConstant

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // init Context
        AppConstant.context = this
    }
}