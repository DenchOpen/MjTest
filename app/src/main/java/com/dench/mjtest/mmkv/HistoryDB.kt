package com.dench.mjtest.mmkv

import com.dench.mjtest.data.HistoryData
import com.dench.netlib.GsonHelper
import com.dench.netlib.NetManager
import com.tencent.mmkv.MMKV
import kotlin.math.max

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
        val result = mutableListOf<HistoryData>()
        val kv = MMKV.mmkvWithID("history")
        if (len == 0) len = kv.decodeInt("len")
        for (i in len..0) {
            val v = kv.decodeString("$i")
            result.add(GsonHelper.fromJson(v, HistoryData::class.java)!!)
        }
        return result
    }

    fun getLastPage(): MutableList<HistoryData> {
        val result = mutableListOf<HistoryData>()
        val kv = MMKV.mmkvWithID("history")
        if (len == 0) len = kv.decodeInt("len")
        for (i in len..max(len - 20, 0)) {
            val v = kv.decodeString("$i")
            result.add(GsonHelper.fromJson(v, HistoryData::class.java)!!)
        }
        return result
    }
}