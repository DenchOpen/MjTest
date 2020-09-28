package com.dench.mjtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dench.mjtest.data.HistoryData
import com.dench.mjtest.mmkv.HistoryDB

class HistoryVM : ViewModel() {
    val historyList = MutableLiveData<MutableList<HistoryData>>().apply {
        postValue(HistoryDB.getHistory())
    }
}