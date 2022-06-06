package com.artjuna.artjuna_app.ui.addproduct.adapter

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artjuna.artjuna_app.databinding.ItemCategoryBinding
import com.artjuna.artjuna_app.databinding.ItemCategoryPickBinding
import com.artjuna.artjuna_app.ui.home.adapter.CatAdapter


class CatPickAdapter() :
    RecyclerView.Adapter<CatPickAdapter.ViewHolder>() {
    private var lastCheckedPosition = -1

    private val list = ArrayList<String>()

    fun setList(res:List<String>){
        list.clear()
        list.addAll(res)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryPickBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: ItemCategoryPickBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:String, position: Int){
            binding.catItem.text = item
            binding.catItem.isChecked = position == lastCheckedPosition
            itemView.setOnClickListener {
                val copyOfLastCheckedPosition = lastCheckedPosition
                lastCheckedPosition = position
                notifyItemChanged(copyOfLastCheckedPosition)
                notifyItemChanged(lastCheckedPosition)
                notifyDataSetChanged()
            }
        }
    }
}