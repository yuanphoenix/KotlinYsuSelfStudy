package com.example.ysuselfstudy.ui.campuscard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.logic.Repository
import com.example.ysuselfstudy.logic.UploadRoom

class CampusCardViewModel : ViewModel() {
    private val card = MutableLiveData<Int>()

    /**
     * 获取校园卡余额
     */
    fun getCrad() {
        card.value = card.value
    }

    var surplus = Transformations.switchMap(card) {
       //UploadRoom.SpideSchool(
//            "http://202.206.243.9/zjdxgc/mycjcx/kjscx.asp" //燕大地址
//        )
        Repository.getEmptyRoom()
    }
}
