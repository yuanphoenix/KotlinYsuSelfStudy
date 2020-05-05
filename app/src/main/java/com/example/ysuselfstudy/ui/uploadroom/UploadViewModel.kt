package com.example.ysuselfstudy.ui.uploadroom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.logic.UploadRoom

class UploadViewModel : ViewModel() {

    private val card = MutableLiveData<Int>()

    /**
     * 进度
     */
    fun getCrad() {
        card.value = card.value
    }

    var process = Transformations.switchMap(card) {
        UploadRoom.SpideSchool(
            "http://202.206.243.9/zjdxgc/mycjcx/kjscx.asp" //燕大地址
        )

    }
}
