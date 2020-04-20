package com.example.ysuselfstudy.ui.grade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.data.Grade
import com.example.ysuselfstudy.logic.Repository

class GradeViewModel : ViewModel() {
    var gradeList = MutableLiveData<Grade>()
    fun getGrade() {
        gradeList.value = gradeList.value
    }

    //真正的主角
    var list = Transformations.switchMap(gradeList) { it ->
        Repository.getGrade()
    }
}
