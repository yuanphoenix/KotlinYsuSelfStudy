package com.example.ysuselfstudy.ui.classschedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.logic.Dao
import com.example.ysuselfstudy.logic.Repository
import com.example.ysuselfstudy.logic.getWeek

class ClassScheduleViewModel : ViewModel() {
    val timeNode = ArrayList<String>()
    var week = MutableLiveData<String>()

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
        week.value = "${getWeek()}周"
    }

    private val course = MutableLiveData<Boolean>()
    private var choiceweek = -1


    var nowWeekCourse = Transformations.switchMap(course) {
        Repository.getTimeStable(it, choiceweek) as LiveData<Any>?
    }

    fun getCourse() {
        //如果nowWeekCourse已经有数据了，那么就不改变数据
        if (nowWeekCourse.value == null)
            course.value = true
    }

    fun getCourse(int: Int) {
        choiceweek = int
        week.value = "${choiceweek}周"
        course.value = course.value
    }


    fun clearCourse(login: Boolean) {
        Dao.deleteAllCourse()
        course.value = login
    }

    val picPath = MutableLiveData<String>()


    fun setPath(path:String){
        picPath.value=path
    }


}
