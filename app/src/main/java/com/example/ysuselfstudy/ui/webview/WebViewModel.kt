package com.example.ysuselfstudy.ui.webview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.data.Information
import com.example.ysuselfstudy.logic.Repository

class WebViewModel : ViewModel() {
    private val uptemp = MutableLiveData<Information>()
    fun upload(information: Information) {
        uptemp.value = information
    }

    var upResult = Transformations.switchMap(uptemp) {
        Repository.uploadCollectInfor(it)
    }
}