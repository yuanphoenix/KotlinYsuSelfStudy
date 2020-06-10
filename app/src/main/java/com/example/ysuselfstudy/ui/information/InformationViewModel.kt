package com.example.ysuselfstudy.ui.information

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.logic.Repository

class InformationViewModel : ViewModel() {
    private var info = MutableLiveData<String>()
    private var aa = 0
    private val TAG = "InformationViewModel"


    var listOfInformation = Transformations.switchMap(info) { it ->
        Repository.getInformation()
    }

    var myListInfor = Transformations.switchMap(info) {
        Repository.getCollectInform()

    }


    fun getInforList() {
        if (info.value != "he")
            info.value = "he"
    }

}
