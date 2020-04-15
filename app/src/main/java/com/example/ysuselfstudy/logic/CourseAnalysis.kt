package com.example.ysuselfstudy.logic

import android.graphics.Color
import com.example.ysuselfstudy.data.Course
import org.jsoup.nodes.Document
import java.util.*

/**
 * @author  Ahyer
 * @date  2020/4/13 19:16
 * @version 1.0
 */


object CourseAnalysis {

    private val time = HashMap<String, Int>()
    private val colorList = ArrayList<Int>()
    init {
        initHash()
    }

    /**
     * 解析课程表
     *
     * @param parse
     */
    fun analysisCourse(parse: Document?):Boolean {
        if (parse == null) return false

        val temp: MutableList<Array<String>> =
            ArrayList()
        val select = parse.select("td:contains(第)")
        for (e in select) {
            val s = e.text().split(" ").toTypedArray()
            temp.add(s)
        }
        var position = 0
        temp.removeAt(0)
        val courseList: MutableList<Course> = ArrayList<Course>()
        for (i in temp.indices) {
            if (time.containsKey(temp[i][0])) position =
                time[temp[i][0]]!!
            val course = Course()
            var j = 0
            var swicthInt = 0
            while (j < temp[i].size && temp[i].size >= 4) {
                var store = temp[i][j]
                when (swicthInt % 5) {
                    0 -> if (isCourseTime(temp[i][j])) { //课程设计时间
                        timeSpacialAnalsis(course, store)
                        swicthInt += 3
                    } else {
                        if (store.contains("（")) {
                            val pos = store.indexOf("（")
                            store = store.substring(0, pos)
                        }
                        course.courseName = store
                    }
                    1 -> course.nature = store
                    2 -> {
                        course.beginNode = position
                        timeMethod(course, store)
                    }
                    3 -> course.teacher = store
                    4 -> {
                        course.position = store
                        course.continued = 2
                        val course1 = Course(course)
                        courseList.add(course1)
                    }
                }
                swicthInt++
                j++
            }
        }
        Dao.saveAllCourse(courseList)
        return true
    }


    private fun timeSpacialAnalsis(course: Course, store: String) {
        if (Character.isDigit(store[0])) { // 2018年07月10日(08:00-09:35)
            //这个将不会添加
            course.dayOfWeek = 0
            course.beginNode = 0
            course.startWeek = 0
            course.endWeek = 0

        } else { //第15周周6(2017-06-10)第7,8节
            val node = store.substring(store.indexOf(")") + 1)
            val integer = time[node]
            course.beginNode = integer ?: 0
            val weekday =
                store.substring(store.indexOf("(") - 2, store.indexOf("("))
            course.dayOfWeek = time[weekday] ?: 0
            val week = store.substring(1, store.indexOf("(") - 3)
            course.startWeek = week.toInt()
            course.endWeek = week.toInt()
        }
    }

    private fun timeMethod(
        course: Course,
        store: String
    ) { //  周二第5,6节{第1-12周},标准解析法
        val data = store.substring(0, 2) //解出来周几
        course.dayOfWeek = time[data] ?: 0
        var week = store.substring(store.indexOf("{")) //{第1-12周}
        if (week.contains("单") || week.contains("双")) { //单双周判断逻辑
            course.isSingleDoubel = true
            if (week.contains("双")) {
                course.isOdd = false
            }
        }
        week = week.substring(2, week.indexOf("周"))
        val split = week.split("-").toTypedArray()
        course.startWeek = split[0].toInt()
        course.endWeek = split[1].toInt()
    }

    private fun isCourseTime(s: String): Boolean {
        if (s[s.length - 1] == '节') return true
        return if (Character.isDigit(s[0])) true else false
    }

    private fun initHash() {
        time["第1节"] = 1
        time["第2节"] = 2
        time["第3节"] = 3
        time["第4节"] = 4
        time["第5节"] = 5
        time["第6节"] = 6
        time["第7节"] = 7
        time["第8节"] = 8
        time["第9节"] = 9
        time["第10节"] = 10
        time["第11节"] = 11
        time["第12节"] = 12
        time["周一"] = 1
        time["周二"] = 2
        time["周三"] = 3
        time["周四"] = 4
        time["周五"] = 5
        time["周六"] = 6
        time["周日"] = 7
        time["第1,2节"] = 1
        time["第3,4节"] = 3
        time["第5,6节"] = 5
        time["第7,8节"] = 7
        time["第9,10节"] = 9
        time["第11,12节"] = 11
        time["周1"] = 1
        time["周2"] = 2
        time["周3"] = 3
        time["周4"] = 4
        time["周5"] = 5
        time["周6"] = 6
        time["周7"] = 7
        colorList.add(Color.parseColor("#52CC33"))
        colorList.add(Color.parseColor("#7444BB"))
        colorList.add(Color.parseColor("#5577AA"))
        colorList.add(Color.parseColor("#EEC211"))
        colorList.add(Color.parseColor("#EEC211"))
        colorList.add(Color.parseColor("#00ccff"))
    }

}