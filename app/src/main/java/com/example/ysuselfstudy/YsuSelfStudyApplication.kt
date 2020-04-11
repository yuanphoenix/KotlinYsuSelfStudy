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
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        LitePal.initialize(context)
    }
}