package com.example.ysuselfstudy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ysuselfstudy.R

/**
 * @author  Ahyer
 * @date  2020/4/16 9:48
 * @version 1.0
 */
class WeekAdapter(val mData: List<String>) : RecyclerView.Adapter<WeekAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val texiview: TextView

        init {
            texiview = view.findViewById(R.id.node_course)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.time_recy_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 12

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.texiview.text = mData[position]
    }

}