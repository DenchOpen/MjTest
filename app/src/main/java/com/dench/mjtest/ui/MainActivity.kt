package com.dench.mjtest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dench.mjtest.R
import com.dench.mjtest.databinding.ActivityMainBinding
import com.dench.mjtest.viewmodel.MainActivityVM
import com.dench.netlib.GsonHelper

class MainActivity : AppCompatActivity() {
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
        viewModel.gaData.observe(this, Observer { value ->
            dataBinding.data = value
        })

        viewModel.startTask()
    }


}