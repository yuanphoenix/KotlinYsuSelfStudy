package com.example.ysuselfstudy.ui.emptyroom

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TimePicker.OnTimeChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ysuselfstudy.R
import razerdp.basepopup.BasePopupWindow
import kotlin.math.log

/**
 * @author  Ahyer
 * @date  2020/4/9 11:49
 * @version 1.0
 */
class TimePopWindow(var con: Context) :
    BasePopupWindow(con) {
    private lateinit var listener: OnCertainClickListener

    init {
        var certifyBtn: Button = findViewById(R.id.certify_btn)
        var beginTime: MyTimePicker = findViewById(R.id.begin_time)
        var endTime: MyTimePicker = findViewById(R.id.end_ime)
        popupGravity = Gravity.BOTTOM
        bindEvent(beginTime, endTime)

        certifyBtn.setOnClickListener {
            listener.clickCertain()
            this.onBackPressed()
        }
    }

    interface OnCertainClickListener {
        fun clickCertain()
    }

    fun setOnCertainClickListener(listen: OnCertainClickListener) {
        listener = listen
    }

    private fun bindEvent(beginTime: MyTimePicker, endTime: MyTimePicker) {

        beginTime.setIs24HourView(true)

        endTime.setIs24HourView(true)
        beginTime.setOnTimeChangedListener(OnTimeChangedListener { view, hourOfDay, minute ->
            if (endTime.myHour < beginTime.myHour) {
                endTime.myHour = beginTime.myHour
            } else if (endTime.myHour === beginTime.myHour) if (endTime.myMinute <= beginTime.myMinute) endTime.myMinute =
                beginTime.myMinute - 1

        })
        endTime.setOnTimeChangedListener(OnTimeChangedListener { view, hourOfDay, minute ->
            if (endTime.myHour < beginTime.myHour) endTime.myHour =
                beginTime.myHour else if (endTime.myHour === beginTime.myHour) if (endTime.myMinute <= beginTime.myMinute) endTime.myMinute
            beginTime.myMinute + 1

        })
    }


    override fun onCreateContentView(): View {
        return createPopupById(R.layout.time_bottom_layout)
    }
}