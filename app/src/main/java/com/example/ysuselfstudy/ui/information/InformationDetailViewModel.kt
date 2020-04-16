package com.example.ysuselfstudy.ui.information

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.logic.Repository

class InformationDetailViewModel : ViewModel() {
    private val url = MutableLiveData<String>()
    fun getInformation(urlOutside: String?) {
        url.value = urlOutside
    }
    var html = Transformations.switchMap(url) { urlOutside ->
        Repository.getDetailInformation(urlOutside)
    }

}
