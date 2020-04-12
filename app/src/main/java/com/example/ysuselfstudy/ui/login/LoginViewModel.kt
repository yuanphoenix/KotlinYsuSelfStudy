package com.example.ysuselfstudy.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.data.User
import com.example.ysuselfstudy.logic.Repository

class LoginViewModel : ViewModel() {
    private val condition = MutableLiveData<User>()

    val state=Transformations.switchMap(condition){ input: User? ->
        Repository.getLoginState(input!!.number, input.eduPassword)

    }

    fun getLogin(user:User) {
        condition.value=user

    }


}
