package com.example.ysuselfstudy.network

import com.example.ysuselfstudy.data.EmptyRoom
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author  Ahyer
 * @date  2020/4/11 10:50
 * @version 1.0
 */
interface RoomService {

    @GET("SelfStudy/HengServlet")
    fun getRoom(@Query("method") state: String): Call<List<EmptyRoom>>

}
