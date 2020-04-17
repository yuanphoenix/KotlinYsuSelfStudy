package com.example.ysuselfstudy

import androidx.lifecycle.*
import com.example.ysuselfstudy.data.QQ
import com.example.ysuselfstudy.data.User
import com.example.ysuselfstudy.logic.Dao
import com.example.ysuselfstudy.logic.Repository
import org.litepal.LitePal

class MainViewModel : ViewModel() {
    private val TAG = "MainViewModel"

    enum class AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED,        // The user has authenticated successfully
        INVALID_AUTHENTICATION,  // Authentication failed
    }

    val authenticationState = MutableLiveData<AuthenticationState>()
    var username: String

    init {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED //这里需要修改判断验证状态
        username = ""
    }

    fun refuseAuthentication() {
        authenticationState.value = AuthenticationState.UNAUTHENTICATED
    }

    private val condition = MutableLiveData<User>()

    var state = Transformations.switchMap(condition) { input: User? ->
        Repository.getLoginState(input!!.number, input.eduPassword)
    }


    fun getLogin(user: User) {
        condition.value = user
    }

    fun authenticate(username: String, password: String) {
        getLogin(User(number = username, eduPassword = password))
    }

fun deleteRoom()=Dao.deleteRoom()
    val headSculpture: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    fun keepQQLogin() {
        if (Dao.keepQQLogin()) {
            headSculpture.value = LitePal.findFirst(QQ::class.java).image
        }
    }
}
