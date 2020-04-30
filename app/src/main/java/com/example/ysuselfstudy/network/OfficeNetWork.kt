package com.example.ysuselfstudy.network

import android.util.Log
import com.example.ysuselfstudy.data.Information
import com.example.ysuselfstudy.logic.Dao
import okhttp3.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
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
                        //获取首页信息，例如实验课以及GBK内码格式的姓名。
                        val document =
                            Jsoup.connect(response.request().url().toString())
                                .cookies(COOKIE_MAP)
                                .get()

                        //获取学生姓名
                        try {
                            val stuname = document.select("span[id=xhxm]")
                            var name = stuname[0].text()//李强同学
                            Log.d(TAG, "name: " + name);
                            //截取姓名
                            name = name.substring(0, name.length - 2)//李强
                            val sb = StringBuilder()
                            val bytes =
                                name.toByteArray(charset("GBK")) //将姓名转为GBK内码格式
                            for (b in bytes) {
                                sb.append(
                                    "%" + Integer.toHexString((b.toInt() and 0xff)).toUpperCase()
                                )
                            }
                            Dao.saveStu(nums, password, sb.toString())
                        } catch (e: Exception) {
                            e.printStackTrace()
                            continuation.resume(false)
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

    suspend fun getCourse(): Document {
        return suspendCoroutine { continuation ->
            val user = Dao.getStu()
            val url =
                "http://202.206.243.62/xskbcx.aspx?xh=${user.number}&xm=${user.gbkName}&gnmkdm=N121603"
            val referrer = "http://202.206.243.62/xs_main.aspx?xh=${user.number}"
            val document = Jsoup.connect(url)
                .cookies(COOKIE_MAP)
                .referrer(referrer)
                .post()
            continuation.resume(document)

        }

    }

    /**
     * 获取考试成绩
     */
    suspend fun getGrade(): Document {
        return suspendCoroutine { continuation ->
            val user = Dao.getStu()
            var url =
                "http://202.206.243.62/mycjcx/xscjcx.asp?xh=${user.number}&xm=${user.gbkName}&gnmkdm=N121632"
            val referer = "http://202.206.243.62/xs_main.aspx?xh=${user.number}"
            var document =
                Jsoup.connect(url)
                    .cookies(COOKIE_MAP)
                    .referrer(referer)
                    .post()
            continuation.resume(document)
        }
    }

    /**
     * 解析获取考试的界面
     */
    suspend fun getExam(): Document {
        return suspendCoroutine { continuation ->
            val user = Dao.getStu()
            val url =
                "http://202.206.243.62/xskscx.aspx?xh=${user.number}&xm=${user.gbkName}&gnmkdm=N121604"
            val referer = "http://202.206.243.62/xs_main.aspx?xh=${user.number}"
            val document = Jsoup.connect(url)
                .cookies(COOKIE_MAP)
                .referrer(referer)
                .post()
            continuation.resume(document)
        }


    }

    /**
     * 获取通知
     */
    suspend fun getInformation(): ArrayList<Information>? {
        return suspendCoroutine { continuation ->
            val infoList = ArrayList<Information>()
            val document =
                Jsoup.connect("https://jwc.ysu.edu.cn/tzgg1.htm")
                    .get()
            val alignLefts: Elements = document.getElementsByClass("alignLeft")
            val a: Elements = alignLefts.select("a")
            val alignLRights = document.getElementsByClass("alignRight")
            for (i in a.indices) {
                var href = a[i].attr("href")
                val title = a[i].attr("title")
                val time = alignLRights[i].text()
                if (!href.contains("https")) {
                    href = "https://jwc.ysu.edu.cn/$href"
                }
                infoList.add(Information(title, href, time))
            }
            continuation.resume(infoList)

        }
    }

    /**
     * 返回详细的通告的html
     */
    suspend fun getDetailInformation(url: String): String {
        return suspendCoroutine { continuation ->
            var document = Jsoup.connect(url).get()
            val m2_f = document.getElementsByClass("m2_f")
            val div: Element = m2_f.select("div")[1]
            continuation.resume(div.html())
        }
    }


}



