package com.dench.mjtest.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dench.mjtest.base.BaseActivity
import com.dench.mjtest.databinding.ActivityHistoryBinding
import com.dench.mjtest.databinding.ActivityMainBinding
import com.dench.mjtest.ui.adapter.HistoryAdapter
import com.dench.mjtest.viewmodel.HistoryVM
import com.dench.mjtest.viewmodel.MainVM
import com.google.android.material.snackbar.Snackbar

class HistoryActivity : BaseActivity() {
    private lateinit var viewModel: HistoryVM
    private lateinit var dataBinding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryVM::class.java)
        dataBinding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        // init
        initView()
    }


    private fun initView() {
        initRecyclerView()
        viewModel.historyList.observe(this, { value ->
            val mAdapter = HistoryAdapter(value)
            dataBinding.historyRv.adapter = mAdapter
        })

    }

    // init RecyclerView
    private fun initRecyclerView() {
        val recyclerView = dataBinding.historyRv
        // layoutManager
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        // animator
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

}