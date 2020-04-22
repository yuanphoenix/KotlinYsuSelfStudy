package com.example.ysuselfstudy

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.ysuselfstudy.logic.Dao
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.tauth.Tencent
import org.litepal.LitePal

class YsuSelfStudyApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var tencent: Tencent
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        tencent = Tencent.createInstance("101560830", context)
        LitePal.initialize(context)
        CrashReport.initCrashReport(context, "ec45c746840", true)
    }
}