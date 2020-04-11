package com.example.ysuselfstudy.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * @author  Ahyer
 * @date  2020/4/11 11:04
 * @version 1.0
 */
object ServiceCreator {
    private const val BASE_URL = "http://39.96.163.218:8080/"
    private val retrofit= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun<T> create(serviceClass:Class<T>):T= retrofit.create(serviceClass)
    inline fun <reified T> create(): T = create(T::class.java)


}