package com.example.ysuselfstudy.ui.classschedule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.data.Course
import com.example.ysuselfstudy.logic.Dao
import com.example.ysuselfstudy.logic.Repository
import com.example.ysuselfstudy.logic.getWeek
import com.example.ysuselfstudy.logic.log

class ClassScheduleViewModel : ViewModel() {
    val timeNode = ArrayList<String>()
    lateinit var week: String

    init {
        timeNode.add("1")
        timeNode.add("2")
        timeNode.add("3")
        timeNode.add("4")
        timeNode.add("5")
        timeNode.add("6")
        timeNode.add("7")
        timeNode.add("8")
        timeNode.add("9")
        timeNode.add("10")
        timeNode.add("11")
        timeNode.add("12")
        week = "${getWeek()}周"
    }

    private val course = MutableLiveData<Boolean>()

    var nowWeekCourse = Transformations.switchMap(course) {
        Repository.getTimeStable(course.value)
    }

    fun getCourse() {
        if (nowWeekCourse.value == null)
            course.value = true
    }

    fun clearCourse(login:Boolean){
        Dao.deleteAllCourse()
        course.value = login
    }


}
