package com.example.ysuselfstudy.ui.exam

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.data.Exam
import com.example.ysuselfstudy.logic.Repository

class ExamViewModel : ViewModel() {

    var examList = MutableLiveData<Exam>()
    fun getExam() {
        examList.value = examList.value
    }

    //真正的主角
    var list = Transformations.switchMap(examList) { it ->
        Repository.getExam()
    }
}
