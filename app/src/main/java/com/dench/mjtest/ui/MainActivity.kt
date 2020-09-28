package com.dench.mjtest.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dench.mjtest.base.BaseActivity
import com.dench.mjtest.databinding.ActivityMainBinding
import com.dench.mjtest.viewmodel.MainActivityVM

class MainActivity : BaseActivity() {
    private lateinit var viewModel: MainActivityVM
    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainActivityVM::class.java)
        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        // init
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelTask()
    }

    private fun initView() {
        viewModel.gaData.observe(this, { value ->
            dataBinding.data = value
        })
        viewModel.requestNotice.observe(this, { message ->
            dataBinding.message = message
        })
        viewModel.startTask()
    }

}