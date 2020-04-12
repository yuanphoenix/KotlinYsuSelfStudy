package com.example.ysuselfstudy

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import org.litepal.LitePal

class YsuSelfStudyApplication :Application()
{
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
        var isLogin = false
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        /**
         * if还没登陆，
         * 看数据库里有没有信息，
         * 有的话就登录
         * 没有的话啥都不要干。
         *
         */
        LitePal.initialize(context)
    }
}