package com.example.ysuselfstudy.ui.campuscard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.ysuselfstudy.data.User

import com.example.ysuselfstudy.logic.Dao
import com.example.ysuselfstudy.logic.Repository


class CampusCardViewModel : ViewModel() {

    enum class TodayAuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED,        // The user has authenticated successfully
    }

    val authenticationState = MutableLiveData<TodayAuthenticationState>()

    init {
        authenticationState.value = TodayAuthenticationState.UNAUTHENTICATED
    }

    private var templogin = MutableLiveData<User>()

    private fun isLogined(): Boolean =
        !(!Dao.isStuEmpty() && Dao.getStu().todaySchoolPassword.equals(""))


    var loginState = Transformations.switchMap(templogin) {
        Repository.getCardSurplus(it)
    }


    private fun login(number: String, password: String) {
        templogin.value = User(number = number, todaySchoolPassword = password)
    }

    fun loginRoute() {
        if (isLogined()) {
            authenticationState.value = TodayAuthenticationState.AUTHENTICATED
            login(Dao.getStu().number, Dao.getStu().todaySchoolPassword)
        }

    }


}
