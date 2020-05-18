package com.example.ysuselfstudy

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import cn.bmob.v3.Bmob
import com.example.ysuselfstudy.logic.Dao
import com.tencent.bugly.Bugly
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.smtt.sdk.QbSdk
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
        QbSdk.initX5Environment(context, null)
        LitePal.initialize(context)
        Bugly.init(context, "ec45c74684", false)
        Bmob.initialize(this, "95472b5edd3fe00a7bc245de053edb71")
    }
}