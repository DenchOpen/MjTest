package com.dench.mjtest.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dench.mjtest.data.HistoryData
import com.dench.mjtest.databinding.ItemHistoryBinding
import java.text.SimpleDateFormat

class HistoryAdapter(private val datas: MutableList<HistoryData>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryVH {
        val dataBinding =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryVH(dataBinding)
    }

    override fun onBindViewHolder(holder: HistoryVH, position: Int) {
        holder.bind(position, datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    inner class HistoryVH(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, data: HistoryData) {
            binding.data = data
            if (data.success) {
                binding.desc.text = "服务成功"
                binding.desc.setTextColor(Color.parseColor("green"))
            } else {
                binding.desc.text = data.ext
                binding.desc.setTextColor(Color.parseColor("red"))
            }
            binding.time.text = SimpleDateFormat("MM-dd hh:mm:ss").format(data.timeMill)
        }

    }
}