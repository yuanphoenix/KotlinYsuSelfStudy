package com.example.ysuselfstudy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.data.Exam
import com.example.ysuselfstudy.databinding.ExamItemLayoutBinding

/**
 * @author  Ahyer
 * @date  2020/4/26 11:19
 * @version 1.0
 */

class ExamAdapter(var mData: ArrayList<Exam>) : RecyclerView.Adapter<ExamAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ExamItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ExamItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.exam_item_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var bean = mData[position]
        holder.binding.bean = bean
    }
}