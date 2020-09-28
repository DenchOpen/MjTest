package com.dench.mjtest.repository

import com.dench.mjtest.data.GAData
import com.dench.netlib.NetManager
import retrofit2.http.GET

interface MjRepo {
    companion object {
        fun create(): MjRepo {
            return NetManager.instance.create(MjRepo::class.java)
        }
    }

    @GET("/")
    suspend fun requestApi(): GAData

}