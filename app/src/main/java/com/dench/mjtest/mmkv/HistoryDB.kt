package com.dench.mjtest.mmkv

import android.util.Log
import com.dench.mjtest.data.HistoryData
import com.dench.netlib.GsonHelper
import com.dench.netlib.NetManager
import com.tencent.mmkv.MMKV

// 记录请求历史
object HistoryDB {

    private var len = 0

    fun recordHistory(url: String, success: Boolean, ext: String? = "") {
        val kv = MMKV.mmkvWithID("history")
        val value = GsonHelper.toJson(
            HistoryData(
                System.currentTimeMillis(),
                NetManager.url + url, success, ext
            )
        )
        if (len == 0) len = kv.decodeInt("len")
        len += 1
        kv.encode("$len", value)
        kv.encode("len", len)
    }

    fun getHistory(): MutableList<HistoryData> {
        Log.d("HistoryDB", "getHistory()")
        val result = mutableListOf<HistoryData>()
        val kv = MMKV.mmkvWithID("history")
        if (len == 0) len = kv.decodeInt("len")
        for (i in 1..len) {
            val v = kv.decodeString("$i")
            if (v != null) {
                Log.d("$i", v)
                result.add(0, GsonHelper.fromJson(v, HistoryData::class.java)!!)
            } else {
                Log.e("$i", "empty!!!")
            }
        }
        return result
    }
}