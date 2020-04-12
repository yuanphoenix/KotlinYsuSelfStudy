package com.example.ysuselfstudy.network

import android.util.Log

import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * @author  Ahyer
 * @date  2020/4/11 22:24
 * @version 1.0
 * 封装了 Okhttp，和Jsoup等网络控件，用来做好爬虫
 */
object OfficeNetWork {
    private val TAG = "OfficeNetWork"
    private val OFFICE_URL = "http://202.206.243.62/default2.aspx"
    private val CODE_URL = "http://202.206.243.62/CheckCode.aspx"

    var client = OkHttpClient.Builder().cookieJar(object : CookieJar {
        private val cookieStore = HashMap<String, List<Cookie>>()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            cookieStore[url.host()] = cookies
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            val cookies = cookieStore[url.host()]
            return cookies ?: ArrayList()
        }
    }).build()

    /**
     * 登录的具体实现，返回值为success 或者 failure
     */
  suspend fun login(nums: String, password: String,code:String): String {
         return suspendCoroutine { continuation ->


             val requestBody = FormBody.Builder()
                 .add("__VIEWSTATE", "dDwxNTMxMDk5Mzc0Ozs+cgOhsy/GUNsWPAGh+Vu0SCcW5Hw=")
                 .add("txtUserName", nums)
                 .add("Textbox1", "")
                 .add("TextBox2", password)
                 .add("RadioButtonList1", "学生")
                 .add("Button1", "")
                 .add("txtSecretCode", code)
                 .add("lbLanguage", "")
                 .add("hidPdrs", "")
                 .add("hidsc", "").build();
             val request = Request.Builder()
                 .post(requestBody)
                 .url(OFFICE_URL)
                 .build()
             val newCall = client.newCall(request)
             newCall.enqueue(object : Callback {
                 override fun onFailure(call: Call, e: IOException) {
                     Log.d(TAG, ": " + e.toString());
                     continuation.resume("failure")
                 }

                 override fun onResponse(call: Call, response: Response) {
                     if (response.request().url().toString().equals(OFFICE_URL)) {
                         continuation.resume("failure")
                     } else {
                         continuation.resume("success")
                     }
                 }

             })
         }
     }

    /**
     * 获取验证码
     */
    suspend fun getCode(): ByteArray {
        return suspendCoroutine { continuation ->
            var res = Request.Builder()
                .url(CODE_URL)
                .get()
                .build()
            var call: okhttp3.Call = client.newCall(res)
            val execute = call.execute()
            val temp = execute.body()!!.bytes()
            continuation.resume(temp)
        }
    }



    /**
     * 向自己服务器验证
     */
    suspend fun postCode(byte: ByteArray): String {
        return suspendCoroutine { continuation ->
            val body = RequestBody.create(MediaType.parse("image/jpg"), byte)
            var resquest = Request.Builder()
                .post(body)
                .url("http://39.96.163.218:8080/SelfStudy/HengServlet?method=CrackCode")
                .build()
            val newCall = client.newCall(resquest)
            val execute = newCall.execute()
            val temp = execute.body()!!.string().trim()
            continuation.resume(temp)
        }
    }
}


