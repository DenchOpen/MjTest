package com.dench.netlib

import com.google.gson.Gson

interface GsonHelper {
    companion object {
        // Object to Json
        fun <T> toJson(t: T): String? {
            val gson = Gson()
            return gson.toJson(t)
        }

        // Json to Object
        fun <T> fromJson(json: String?, clazz: Class<T>?): T {
            val gson = Gson()
            return gson.fromJson(json, clazz)
        }
    }
}