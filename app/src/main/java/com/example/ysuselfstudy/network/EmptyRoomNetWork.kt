package com.example.ysuselfstudy.network

import android.util.Log
import org.litepal.LitePal
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
    private val roomService = ServiceCreator.create(RoomService::class.java)
    suspend fun searchRoom(query: String) = roomService.getRoom(query).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                        Log.d("shibai ","失败")
                    Log.d("原因",t.message)
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