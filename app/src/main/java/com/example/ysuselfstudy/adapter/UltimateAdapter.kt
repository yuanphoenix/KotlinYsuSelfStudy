package com.example.ysuselfstudy.adapter

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.logic.Dao
import com.example.ysuselfstudy.logic.showToast


/**
 * @author  Ahyer
 * @date  2020/4/8 18:01
 * @version 1.0
 */

class UltimateAdapter(
    var data: List<UnitData<String, String>>,
    var navController: NavController
) : ExpandAdapte<String, String>(data) {

    override fun bindView(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ExpandAdapte<*, *>.ParentViewHolder) {
            var test: kotlin.String = getItem(position) as kotlin.String
            holder.binding.itemArea.setTextSize(22F)
            holder.binding.itemArea.text = test
        }
        setmyClickListener(object :
            OnItemClickListener {
            override fun OnParentClickListener(paent: String) {

            }

            override fun OnChildClickListenr(child: String) {
                if (Dao.isRoomEmpty()) {
                    "请下拉更新教室数据".showToast()
                } else {
                    val msg = bundleOf("amount" to child)
                    navController.navigate(R.id.roomDetailFragment, msg)
                }
            }
        })



        if (holder is ExpandAdapte<*, *>.ChildViewHolder) {
            var test: kotlin.String = getItem(position) as kotlin.String
            holder.binding.itemArea.text = "    " + test
        }
    }


}