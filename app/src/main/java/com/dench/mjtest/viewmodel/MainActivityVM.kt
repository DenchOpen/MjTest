package com.dench.mjtest.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dench.mjtest.data.GAData
import com.dench.mjtest.extension.callRequest
import com.dench.mjtest.net.NetResult
import com.dench.mjtest.repository.MjRepo
import com.dench.mjtest.utils.AppConstant
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.util.*

class MainActivityVM : ViewModel() {
    private val repo = MjRepo.create()

    val gaData = MutableLiveData<GAData>()

    private var timer: Timer? = null

    override fun onCleared() {
        Log.d(Thread.currentThread().name, "onCleared()")
        super.onCleared()
    }

    fun cancelTask() {
        Log.d(Thread.currentThread().name, "cancelTask()")
        timer?.cancel()
        timer = null
    }

    fun startTask() {
        Log.d(Thread.currentThread().name, "startTask()")
        val timerTask = ApiTimerTask(this)
        timer?.cancel()
        timer = Timer()
        timer?.schedule(timerTask, 5000, 5000)
//        sendGADataRequest()
    }

    private var count = 0
    private fun sendGADataRequest() {
        count++
        viewModelScope.launch {
            val result = callRequest { repo.requestApi() }
            if (result is NetResult.Success) {
                gaData.postValue(result.data)
            } else if (result is NetResult.Error) {
                Toast.makeText(AppConstant.context, result.exception.msg, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    inner class ApiTimerTask(viewModel: MainActivityVM) : TimerTask() {
        private val weakReference = WeakReference(viewModel)
        override fun run() {
            Log.d(Thread.currentThread().name, "schedule a task.")
            weakReference.get()?.sendGADataRequest()
        }
    }

}