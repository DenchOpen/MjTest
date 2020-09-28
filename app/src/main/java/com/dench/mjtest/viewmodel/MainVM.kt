package com.dench.mjtest.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dench.mjtest.data.GAData
import com.dench.mjtest.extension.callRequest
import com.dench.mjtest.mmkv.HistoryDB
import com.dench.mjtest.mmkv.RepositoryDB
import com.dench.mjtest.net.NetResult
import com.dench.mjtest.repository.MjRepo
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class MainVM : ViewModel() {
    private val repo = MjRepo.create()

    val gaData = MutableLiveData<GAData>()
    val requestNotice = MutableLiveData<String>().apply {
        value = "空数据"
    }

    override fun onCleared() {
        Log.d(Thread.currentThread().name, "onCleared()")
        super.onCleared()
    }

    fun cancelTask() {
        Log.d(Thread.currentThread().name, "cancelTask()")
        task?.let { handler?.removeCallbacks(task) }
    }

    fun startTask() {
        Log.d(Thread.currentThread().name, "startTask()")
        // load native
        if (gaData.value == null) {
            val t = RepositoryDB.fetchUrlData("/", GAData::class.java)
            if (t != null) {
                requestNotice.value = "当前显示的是历史数据"
                gaData.value = t
            }
        }
        //start Timer
        sendNext()
    }

    private var count = 0
    private fun sendGADataRequest() {
        // start Request
        count++
        requestNotice.value = "第${count}次服务请求中..."
        viewModelScope.launch {
            val result = callRequest { repo.requestApi() }
            if (result is NetResult.Success) {
                RepositoryDB.recordUrlData("/", result.data)
                gaData.postValue(result.data)
                requestNotice.value = "第${count}次服务请求成功"
                HistoryDB.recordHistory("/", true, "请求成功")
                sendNext()
            } else if (result is NetResult.Error) {
                requestNotice.value = "第${count}次服务请求失败: ${result.exception.msg}"
                HistoryDB.recordHistory("/", false, result.exception.msg)
                sendNext()
            }
        }
    }

    private var handler: Handler? = null
    private var task: ApiRunnableTask? = null
    private fun sendNext() {
        if (handler == null) handler = Handler(Looper.getMainLooper())
        if (task == null) task = ApiRunnableTask(this)
        handler!!.postDelayed(task, 5 * 1000)
    }

    inner class ApiRunnableTask(viewModel: MainVM) : Runnable {
        private val weakReference = WeakReference(viewModel)
        override fun run() {
            Log.d(Thread.currentThread().name, "schedule a task.")
            weakReference.get()?.sendGADataRequest()
        }
    }

}