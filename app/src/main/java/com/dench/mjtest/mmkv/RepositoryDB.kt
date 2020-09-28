package com.dench.mjtest.mmkv

import com.dench.netlib.GsonHelper
import com.dench.netlib.NetManager
import com.tencent.mmkv.MMKV

// repo DB
object RepositoryDB {
    fun <T> recordUrlData(url: String, data: T) {
        val key = NetManager.url + url
        val kv = MMKV.mmkvWithID("app")
        val value = GsonHelper.toJson(data)
        kv.removeValueForKey(key)
        kv.encode(key, value)
    }

    fun <T> fetchUrlData(url: String, clazz: Class<T>): T? {
        val key = NetManager.url + url
        val kv = MMKV.mmkvWithID("app")
        val value = kv.decodeString(key)
        return GsonHelper.fromJson(value, clazz)
    }
}