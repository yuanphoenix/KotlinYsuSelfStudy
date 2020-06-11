package com.example.ysuselfstudy.ui

import android.view.Gravity
import android.view.View
import android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS
import android.widget.Button
import android.widget.TimePicker
import android.widget.TimePicker.OnTimeChangedListener
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.ui.emptyroom.RoomFragment
import razerdp.basepopup.BasePopupWindow

/**
 * @author  Ahyer
 * @date  2020/4/9 11:49
 * @version 1.0
 */
class TimePopWindow(var fragment: RoomFragment) :
    BasePopupWindow(fragment) {

    init {
        var certifyBtn: Button = findViewById(R.id.certify_btn)
        var beginTime: MyTimePicker = findViewById(R.id.begin_time)
        var endTime: MyTimePicker = findViewById(R.id.end_ime)
        beginTime.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        endTime.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        popupGravity = Gravity.BOTTOM
        bindEvent(beginTime, endTime)

        certifyBtn.setOnClickListener {
            fragment.viewModel.time.value =
                String.format("%02d:%02d-%02d:%02d", beginTime.myHour, beginTime.myMinute, endTime.myHour, endTime.myMinute)
            this.onBackPressed()
        }
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
            if (endTime.myHour < beginTime.myHour) endTime.myHour = beginTime.myHour
            else if (endTime.myHour === beginTime.myHour)
                if (endTime.myMinute <= beginTime.myMinute) endTime.myMinute =
                    beginTime.myMinute + 1

        })
    }


    override fun onCreateContentView(): View {
        return createPopupById(R.layout.time_bottom_layout)
    }
}