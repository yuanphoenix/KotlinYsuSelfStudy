package com.example.ysuselfstudy.logic

import android.util.DisplayMetrics
import android.util.Log
import com.example.ysuselfstudy.YsuSelfStudyApplication
import org.jsoup.nodes.Element
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
    calendar.set(2020, Calendar.FEBRUARY, 24, 0, 0, 0)
    val now = Calendar.getInstance()
    val day =
        ((now.timeInMillis - calendar.getTimeInMillis()) / (1000 * 3600 * 24)).toInt()
    return day / 7 + 1
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



