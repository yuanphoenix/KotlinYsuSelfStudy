package com.example.ysuselfstudy.ui.classschedule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.data.Course
import com.example.ysuselfstudy.logic.Dao
import com.example.ysuselfstudy.logic.Repository

class ClassScheduleViewModel : ViewModel() {
    private val course = MutableLiveData<Course>()

    var nowWeekCourse = Transformations.switchMap(course) {
        Repository.getTimeStable()
    }

    fun getCourse() {
        course.value = course.value
    }

}
