package com.example.ysuselfstudy.network

import android.util.Log
import android.widget.Toast
import com.example.ysuselfstudy.data.Exam
import okhttp3.*
import org.jsoup.Jsoup
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
    private val COOKIE_MAP = mutableMapOf<String, String>()


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
    suspend fun login(nums: String, password: String, code: String): Boolean {
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
                    continuation.resume(false)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.request().url().toString().equals(OFFICE_URL)) {
                        continuation.resume(false)
                    } else {
                        val loadForRequest =
                            client.cookieJar().loadForRequest(HttpUrl.get(OFFICE_URL))
                        //取出所有的 Cookies
                        for (cookie in loadForRequest) {
                            COOKIE_MAP[cookie.name()] = cookie.value()
                        }

                        continuation.resume(true)

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

  /* suspend fun getExam(): List<Exam> {
         return suspendCoroutine { continuation ->
             val connection = Jsoup.connect(exam)
                 .header("Cookie", AllString.Cookie)
                 .header("Host", AllString.YSU_URL)
                 .referrer(referer)
                 .cookies(COOKIE_MAP)
                 .data("xh", xuehao)
                 .data("xm", AllString.GBKNAME)
                 .data("gnmkdm", "N121604") //.data("__EVENTTARGET", "xnd")
                 .post()

             //如果还未评价。这是一个优化点
             //如果还未评价。这是一个优化点
             val aab = connection.body().text()
             //  AllString.log("探测点" + aab);

             //  AllString.log("探测点" + aab);
             if (aab.length == 0) {
                 runOnUiThread(Runnable {
                     Toast.makeText(this@ExamActivity, "请登录教务系统进行教学评价", Toast.LENGTH_LONG).show()
                     com.example.ysuselfstudy.ui.ExamActivity.have = true
                 })
             }
             //获取了所有爬取的信息。
             //获取了所有爬取的信息。
             val elements = connection.select("td")
         }
     }

 */
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


