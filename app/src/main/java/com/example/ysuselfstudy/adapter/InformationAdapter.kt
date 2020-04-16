package com.example.ysuselfstudy.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.data.Information
import com.google.android.material.button.MaterialButton

/**
 * @author  Ahyer
 * @date  2020/4/16 14:50
 * @version 1.0
 */
class InformationAdapter(val mData: ArrayList<Information>, var navController: NavController) :
    RecyclerView.Adapter<InformationAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var textView: TextView
        lateinit var btn: MaterialButton

        init {
            textView = view.findViewById(R.id.info_title)
            btn = view.findViewById(R.id.time_btn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.inform_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = mData.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = mData[position].title
        holder.btn.text = mData[position].time
        holder.itemView.setOnClickListener {
            val msg = bundleOf("amount" to mData[position].url)
            navController.navigate(R.id.informationDetailFragment, msg)
        }
    }

}