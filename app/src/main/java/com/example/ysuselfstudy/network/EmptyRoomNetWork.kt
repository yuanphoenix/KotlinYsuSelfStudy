package com.example.ysuselfstudy.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
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

}