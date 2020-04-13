package com.example.ysuselfstudy.ui

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.TimePicker

class MyTimePicker : TimePicker {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    var myHour: Int
        get() = if (Build.VERSION.SDK_INT < 23) {
            currentHour
        } else hour
        set(hour) {
            if (Build.VERSION.SDK_INT < 23) currentHour = hour else setHour(hour)
        }

    var myMinute: Int
        get() {
            return if (Build.VERSION.SDK_INT < 23) currentMinute else minute
        }
        set(minute) {
            if (Build.VERSION.SDK_INT < 23) currentMinute = minute else setMinute(minute)
        }
}