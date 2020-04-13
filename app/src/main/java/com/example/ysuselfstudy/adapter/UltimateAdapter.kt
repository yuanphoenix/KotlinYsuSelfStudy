package com.example.ysuselfstudy.adapter

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ysuselfstudy.R


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
            holder.binding.itemArea.text = test
        }
        setmyClickListener(object :
            OnItemClickListener {
            override fun OnParentClickListener(paent: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun OnChildClickListenr(child: String) {
                val msg = bundleOf("amount" to child)
                navController.navigate(R.id.roomDetailFragment, msg)
            }
        })



        if (holder is ExpandAdapte<*, *>.ChildViewHolder) {
            var test: kotlin.String = getItem(position) as kotlin.String
            holder.binding.itemArea.text = test
        }
    }


}