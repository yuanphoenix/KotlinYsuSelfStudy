package com.example.ysuselfstudy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ysuselfstudy.data.EmptyRoom
import com.example.ysuselfstudy.databinding.RoomItemBinding
import com.example.ysuselfstudy.databinding.UploadFragmentBinding

/**
 * @author  Ahyer
 * @date  2020/5/5 18:24
 * @version 1.0
 */
class RoomAdapter(var mData: ArrayList<EmptyRoom>) :
    RecyclerView.Adapter<RoomAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: RoomItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = RoomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.bean = mData[position]
    }
}