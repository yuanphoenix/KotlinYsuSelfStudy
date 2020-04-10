package com.example.ysuselfstudy.viewmodelfactory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ysuselfstudy.ui.emptyroom.RoomViewModel
import java.util.*

/**
 * @author  Ahyer
 * @date  2020/4/10 10:08
 * @version 1.0
 */
class MyViewModelFactory() :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val calendar = Calendar.getInstance()
        val hours = calendar[Calendar.HOUR_OF_DAY]
        val minutes = calendar[Calendar.MINUTE]
        val temp ="${hours}:${minutes}-${hours}:${minutes}"
        var msg = MutableLiveData<String>(temp)
        return RoomViewModel(msg) as T
    }
}