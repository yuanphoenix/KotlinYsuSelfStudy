package com.example.ysuselfstudy.ui.grade

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.data.GPA
import com.example.ysuselfstudy.logic.Repository

class GPAViewModel : ViewModel() {

    var gpaTemp=MutableLiveData<GPA>()

    fun getGPA(){
        gpaTemp.value = gpaTemp.value
    }

    var GPA=Transformations.switchMap(gpaTemp){
        Repository.getGPA()
    }
}