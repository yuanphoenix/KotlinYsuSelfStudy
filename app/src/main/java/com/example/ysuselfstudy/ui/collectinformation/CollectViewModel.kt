package com.example.ysuselfstudy.ui.collectinformation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.logic.Repository

class CollectViewModel : ViewModel() {
    private val infortemp = MutableLiveData<String>()

    fun getInformationList() {
        infortemp.value = infortemp.value
    }

    var inforList = Transformations.switchMap(infortemp) {
        Repository.getCollectInform()
    }
}