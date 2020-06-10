package com.example.ysuselfstudy.network

import android.util.Log
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.data.Datas
import com.example.ysuselfstudy.data.InformCollect
import com.example.ysuselfstudy.data.Information
import com.example.ysuselfstudy.logic.Dao
import com.example.ysuselfstudy.logic.log
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object CollectNetWork {
    private val TAG = "CollectNetWork"

    /**
     * 插入收藏信息
     */
    suspend fun uploadInform(information: InformCollect): Boolean {

        return suspendCoroutine { continuation ->
            information.save(object : SaveListener<String>() {
                override fun done(p0: String?, p1: BmobException?) {
                    if (p1 == null) {
                        YsuSelfStudyApplication.myinform.add(information)
                        continuation.resume(true)
                    } else {
                        continuation.resume(false)
                    }
                }

            })
        }

    }

    /**
     * 获取收藏列表
     */
    suspend fun getInform(): MutableList<InformCollect>? {
        return suspendCoroutine { continuation ->
            val query = BmobQuery<InformCollect>()
            val qq = Dao.getQQ()
            query.addWhereEqualTo("openID", qq?.openID)
            query.findObjects(object : FindListener<InformCollect>() {
                override fun done(p0: MutableList<InformCollect>?, p1: BmobException?) {
                    continuation.resume(if (p1 == null) p0 else null)
                }
            })

        }
    }

    /**
     * 删除操作
     */
    suspend fun deleteinformCollect(openid: String): Boolean {
        return suspendCoroutine { continuation ->
            val deleteTemp = InformCollect("a", "a", "a", "a")
            deleteTemp.delete(openid, object : UpdateListener() {
                override fun done(p0: BmobException?) {
                    continuation.resume(if (p0 == null) true else false)
                }

            })
        }
    }
}