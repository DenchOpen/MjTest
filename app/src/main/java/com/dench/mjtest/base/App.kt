package com.dench.mjtest.base

import android.app.Application
import com.dench.mjtest.utils.AppConstant
import com.tencent.mmkv.MMKV


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // init Context
        AppConstant.context = this
        // initMMKV
        initMMKV()
    }

    private fun initMMKV() {
        val start = System.currentTimeMillis()
        val rootDir = MMKV.initialize(this)
        println("mmkv rootDir: $rootDir")
        println("mmkv init time spend: ${System.currentTimeMillis() - start}")
    }
}