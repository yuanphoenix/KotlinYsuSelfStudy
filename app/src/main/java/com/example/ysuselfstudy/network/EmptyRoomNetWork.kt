package com.example.ysuselfstudy.network

import android.util.Log
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.QueryListener
import com.example.ysuselfstudy.data.BiyingPic
import com.example.ysuselfstudy.data.EmptyRoom
import com.example.ysuselfstudy.data.UploadRoomMsg
import com.example.ysuselfstudy.logic.Dao
import com.example.ysuselfstudy.logic.getDate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author  Ahyer
 * @date  2020/4/11 10:49
 * @version 1.0
 */
object EmptyRoomNetWork {
    //初始化Retrofit
    private val TAG = "EmptyRoomNetWork"
    private val roomService = ServiceCreator.create(RoomService::class.java)
    suspend fun searchRoom(query: String) = roomService.getRoom(query).await()//注意这里可能有错

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.d("原因", t.message)
                    //如果服务器上没有空教室，那么也会报错,因为服务器返回的不标准。
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    var list = response.body()
                    if (list != null) {
                        continuation.resume(list)
                    }
                }

            })

        }
    }

    suspend fun getEmptyRoom(): String {
        return suspendCoroutine { continuation ->
            val msg = BmobQuery<UploadRoomMsg>()
            var shouldUpload = false
            msg.getObject("lTM53338", object : QueryListener<UploadRoomMsg>() {
                override fun done(p0: UploadRoomMsg?, p1: BmobException?) = if (p0 != null) {
                    //成功
                    if (getDate().substring(0, 10).equals(p0.updatedAt.substring(0, 10))) {
                        //获取了本周的空教室
                        getOtherRoom()
                        var roomList = Gson()
                        var type = object : TypeToken<ArrayList<EmptyRoom>>() {}.type
                        var saveList: ArrayList<EmptyRoom> = roomList.fromJson(p0.room_json, type)

                        Dao.saveRoom(saveList)
                        continuation.resume(p0.nick_name)
                    } else {
                        //日期不对，需要用户上传教室
                        continuation.resume("false")
                    }
                } else {
                    //失败
                    continuation.resumeWithException(RuntimeException(p1.toString()))
                }

            })

            if (!shouldUpload) {

            }


        }
    }

    fun getOtherRoom() {
        val msg = BmobQuery<UploadRoomMsg>()
        msg.getObject("2ec49dadea", object : QueryListener<UploadRoomMsg>() {
            override fun done(p0: UploadRoomMsg?, p1: BmobException?) = if (p0 != null) {
                //获取了本周的空教室
                var roomList = Gson()
                Log.d(TAG, "done: 新教室")
                var type = object : TypeToken<ArrayList<EmptyRoom>>() {}.type
                var saveList: ArrayList<EmptyRoom> = roomList.fromJson(p0.room_json, type)
                Dao.saveRoom(saveList)

            } else {
                //失败

            }

        })
    }

    /**
     * 返回必应首页
     */
    suspend fun SearchForBiYing(): String {
        return suspendCoroutine { continuation ->
            var element: Elements? = null
            var url = "https://cn.bing.com/"
            try {
                val document = Jsoup.connect(url).get()
                element = document.select("link[id=bgLink]")

                var temp = element.attr("href")

                val sub = temp.indexOf("&") //找到第一次出现的地方
                temp = temp.substring(0, sub)
                url += temp
                val biyingPic = BiyingPic(url)
                biyingPic.save()
                continuation.resume(url)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }

    }

}