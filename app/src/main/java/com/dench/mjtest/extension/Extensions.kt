package com.dench.mjtest.extension

import androidx.lifecycle.ViewModel
import com.dench.mjtest.net.DealException
import com.dench.mjtest.net.NetResult

suspend fun <T : Any> ViewModel.callRequest(call: suspend () -> T): NetResult<T> {
    return try {
        NetResult.Success(call())
    } catch (e: Exception) {
        //这里统一处理异常
        e.printStackTrace()
        NetResult.Error(DealException.handlerException(e))
    }
}
