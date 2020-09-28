package com.dench.mjtest.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dench.mjtest.R
import com.dench.mjtest.base.BaseActivity
import com.dench.mjtest.databinding.ActivityMainBinding
import com.dench.mjtest.viewmodel.MainVM

class MainActivity : BaseActivity() {
    private lateinit var viewModel: MainVM
    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainVM::class.java)
        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        // init
        initView()
        registerListener()
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

    private fun registerListener() {
        dataBinding.fab.setOnClickListener {
            val intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

}