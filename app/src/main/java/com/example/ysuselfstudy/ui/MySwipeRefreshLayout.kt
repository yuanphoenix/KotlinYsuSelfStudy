package com.example.ysuselfstudy.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


/**
 * @author  Ahyer
 * @date  2020/4/12 18:37
 * @version 1.0
 */
class MySwipeRefreshLayout : SwipeRefreshLayout {
    constructor(context: Context, attes: AttributeSet) : super(context, attes) {}
    constructor(context: Context) : super(context)

    private var startX = 0
    private var beginScrolll = false
    private var startY: Int = 0
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x.toInt()
                startY = ev.y.toInt()
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                val disX = Math.abs(endX - startX)
                val disY: Int = Math.abs(endY - startY)
                if (disX > disY) {
                    if (!beginScrolll)
                        parent.requestDisallowInterceptTouchEvent(false)
                } else {
                    beginScrolll = true
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                parent.requestDisallowInterceptTouchEvent(false)
                beginScrolll=false
            }
        }
        return super.dispatchTouchEvent(ev)
    }


}