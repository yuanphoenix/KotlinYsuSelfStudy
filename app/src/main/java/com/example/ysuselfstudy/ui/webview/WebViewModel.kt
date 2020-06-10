package com.example.ysuselfstudy.ui.webview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.data.Information
import com.example.ysuselfstudy.logic.Repository

class WebViewModel : ViewModel() {
    private val uptemp = MutableLiveData<Information>()
    private val canceltemp = MutableLiveData<String>()
    fun upload(information: Information) {
        uptemp.value = information
    }

    fun cancel(objectId: String) {
        canceltemp.value = objectId
    }

    var cancelResult = Transformations.switchMap(canceltemp){
        Repository.deleteinformCollect(it)
    }
    var upResult = Transformations.switchMap(uptemp) {
        Repository.uploadCollectInfor(it)
    }



}