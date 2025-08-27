package com.example.unit4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unit4.databinding.ItemLayoutBinding

class AdapterClass(private val dataList: ArrayList<DataClass>) :
    RecyclerView.Adapter<AdapterClass.ViewHolder>() {

    class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataClass) {
            binding.imageView.setImageResource(data.dataImage)
            binding.textView.text = data.dataTitle
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}
