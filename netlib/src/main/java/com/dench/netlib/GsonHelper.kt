package com.dench.netlib

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

interface GsonHelper {
    companion object {
        // Object to Json
        fun <T> toJson(t: T): String? {
            if (t == null) return null
            val gson = Gson()
            return gson.toJson(t)
        }

        // Json to Object
        fun <T> fromJson(json: String?, clazz: Class<T>?): T? {
            try {
                if (json == null) return null
                val gson = Gson()
                return gson.fromJson(json, clazz)
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
                return null
            }
        }
    }
}