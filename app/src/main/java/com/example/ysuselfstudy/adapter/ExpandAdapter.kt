package com.example.ysuselfstudy.adapter

/**
 * @author  Ahyer
 * @date  2020/4/9 15:56
 * @version 1.0
 */
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import com.example.ysuselfstudy.databinding.ListItemAreaBinding

/**
 * @author  Ahyer
 * @date  2020/4/8 11:30
 * @version 1.0
 */
abstract class ExpandAdapte<T, V>(
    var mData: List<UnitData<T, V>>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val GROUP = 0
    val CHILD = 1

    lateinit var myListener: OnItemClickListener


    interface OnItemClickListener {
        fun OnParentClickListener(parent: String)
        fun OnChildClickListenr(child: String)
    }

    open fun setmyClickListener(myClickListener: OnItemClickListener) {
        this.myListener = myClickListener
    }


    inner class ParentViewHolder(var binding: ListItemAreaBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    inner class ChildViewHolder(var binding: ListItemAreaBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    fun getItem(position: Int): Any? {
        var currency = -1
        for (unit in mData) {
            if (unit.folded) {
                currency += 1
                if (currency == position) {
                    return unit.parent!!
                }
            } else {
                currency += 1
                if (currency == position) {
                    return unit.parent!!
                }
                currency = currency + unit.childItems.size
                if (position <= currency) {
                    var index = unit.childItems.size - 1 - (currency - position)
                    return unit.childItems[index]!!
                }
            }
        }
        return null
    }

    /**
     * 获取应该加载的Item数
     * @return
     */
    override fun getItemCount(): Int {
        var result = 0
        for (unit in mData) {
            result = if (unit.folded) {
                result + 1
            } else {
                result + 1 + unit.childItems.size
            }
        }
        return result
    }


    /**
     * 重写了返回ViewType的方法
     */
    override fun getItemViewType(position: Int): Int {
        var currentPosition: Int = -1
        for (unit in mData) {
            if (unit.folded) {
                currentPosition += 1
                if (currentPosition == position) {
                    return GROUP;//父类
                }
            } else {
                currentPosition = currentPosition + 1
                if (currentPosition == position) {
                    return GROUP
                }
                currentPosition = currentPosition + unit.childItems.size
                if (position <= currentPosition) {
                    return CHILD
                }
            }
        }
        return GROUP
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var holder1: RecyclerView.ViewHolder
        when (viewType) {
            GROUP -> {
                holder1 = ParentViewHolder(
                    ListItemAreaBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            CHILD -> {
                holder1 = ChildViewHolder(
                    ListItemAreaBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else ->
                throw Exception("onCreateViewHolder失败")
        }
        return holder1
    }

    private fun getUnit(position: Int): UnitData<T, V>? {

        var currency = -1
        for (unit in mData) {
            if (unit.folded) {
                currency += 1
            } else {
                currency = currency + 1 + unit.childItems.size
            }
            if (currency >= position) {
                return unit
            }
        }
        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bindView(holder, position)

        holder.itemView.setOnClickListener {
            //父类点击事件

            if (holder is ExpandAdapte<*, *>.ParentViewHolder) {
                var unit = getUnit(holder.adapterPosition)
                unit!!.folded = !unit.folded
                if (unit.folded) {
                    notifyItemRangeRemoved(holder.adapterPosition + 1, unit.childItems.size)
                } else {
                    notifyItemRangeInserted(holder.adapterPosition + 1, unit.childItems.size)
                }
            }

            if (holder is ExpandAdapte<*, *>.ChildViewHolder) {
                myListener.OnChildClickListenr(holder.binding.itemArea.text.toString())
            }

        }


    }

    abstract fun bindView(holder: RecyclerView.ViewHolder, position: Int)

    companion object
    class UnitData<T, V>(var parent: T, var childItems: List<V>) {
        var folded = true
    }
}


