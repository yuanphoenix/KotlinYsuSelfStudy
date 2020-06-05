package com.example.ysuselfstudy.logic

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import com.example.ysuselfstudy.YsuSelfStudyApplication
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author  Ahyer
 * @date  2020/4/15 14:22
 * @version 1.0
 */

/**
 * 返回开学的周数
 */
fun getWeek(): Int {
    var calendar = Calendar.getInstance()
    val prefs =
        YsuSelfStudyApplication.context.getSharedPreferences("date", Context.MODE_PRIVATE)
    val longtime = prefs.getLong("begin", 1582473600000)

    calendar.timeInMillis = longtime
    val now = Calendar.getInstance()
    val day =
        ((now.timeInMillis - calendar.timeInMillis) / (1000 * 3600 * 24)).toInt()
    calendar.firstDayOfWeek

    return day / 7 + 1
}


/**
 * 确定开始的周数
 */
fun getBeginDate(week: Int) {
    val calendar = Calendar.getInstance()
    calendar[Calendar.MILLISECOND] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.HOUR_OF_DAY] = 0
    val thisWeek = ((calendar[Calendar.DAY_OF_WEEK] - 2) * 1000 * 3600 * 24).toLong()
    val two = Calendar.getInstance()

    two.timeInMillis = calendar.timeInMillis - thisWeek

    val ha = two.timeInMillis - (week.toLong() - 1) * 7 * 1000 * 3600 * 24

    val beginWeek = Calendar.getInstance()
    beginWeek.timeInMillis = ha

    val editor =
        YsuSelfStudyApplication.context.getSharedPreferences("date", Context.MODE_PRIVATE).edit()
    editor.putLong("begin", beginWeek.timeInMillis)
    editor.apply()
}

/**
 * 日志工具
 */
fun log(out: String) {
    if (out.length > 4000) {
        var i = 0
        while (i < out.length) {
            if (i + 4000 < out.length) {
                Log.i("msg$i", out.substring(i, i + 4000))
            } else Log.i("msg$i", out.substring(i, out.length))
            i += 4000
        }
    } else Log.i("msg", out)
}

/**
 * dp转px
 */
fun getPixelsFromDp(size: Int): Int {
    val metrics = YsuSelfStudyApplication.context.getResources().getDisplayMetrics();
    return size * metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
}

/**
 * 空教室日期设置
 */
fun getDate(): String {
    val date = Calendar.getInstance()
    var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var dateString = dateFormat.format(date.time)
    return dateString
}

fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(YsuSelfStudyApplication.context, this, duration).show()
}

fun setClockDate(date: String): Long {
    val split = date.split("-")

    val resultDate = Calendar.getInstance()
    resultDate[Calendar.MONTH] = split[1].toInt() - 1
    resultDate[Calendar.DATE] = split[2].toInt()
    resultDate[Calendar.MILLISECOND] = 0
    resultDate[Calendar.SECOND] = 0
    resultDate[Calendar.MINUTE] = 0
    resultDate[Calendar.HOUR_OF_DAY] = 7
    return resultDate.timeInMillis
}





