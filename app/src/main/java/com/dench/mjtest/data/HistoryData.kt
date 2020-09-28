package com.dench.mjtest.data

data class HistoryData(
    val timeMill: Long,
    val url: String,
    val success: Boolean,
    val ext: String?
)