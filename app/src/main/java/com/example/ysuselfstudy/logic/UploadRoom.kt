package com.example.ysuselfstudy.logic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.example.ysuselfstudy.data.UploadRoomMsg
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.collections.ArrayList

/**
 * @author  Ahyer
 * @date  2020/5/5 8:46
 * @version 1.0
 */
object UploadRoom {
    private val TAG = "UploadRoom"
    private val roomList = ArrayList<EmptyRoomMirror>()
    private var gson = Gson()

    var schoolBuilding = mutableSetOf<String>()
    fun SpideSchool(url: String) = liveData(Dispatchers.IO) {
        var page = 0
        schoolBuilding.addAll(
            setOf(
                "第一教学楼",
                "第二教学楼",
                "第三教学楼",
                "第四教学楼",
                "西区第一教学楼",
                "西区第二教学楼",
                "西区第三教学楼",
                "西区第四教学楼",
                "西区第五教学楼",
                "里仁教学楼"
            )
        )
        Log.d(TAG, "SpideSchool:上传教室开始 ");
        var process = MutableLiveData<Int>()
        var tempProces = 0
        process.postValue(tempProces)
        emitSource(process)

        try {
            //i是第几节课
            var i = 1
            while (i < 13) {
                var currentTimeNode = true
                var response: Document
                while (currentTimeNode) {
                    response = Jsoup.connect(url)
                        .timeout(20 * 1000)
                        .header("Host", "202.206.243.9")
                        .method(Connection.Method.GET)
                        .maxBodySize(0)
                        .followRedirects(false)
                        .data("yxid", "")
                        .data("sjd", i.toString() + "") //这是第几节课的意思
                        .data("show2", "1")
                        .data("Submit", "按条件显示")
                        .followRedirects(true)
                        .post()
                    Thread.sleep(1000)
                    if (AnalysiRoom(response, i, false)) {
                        currentTimeNode = false
                        // 随后应该找到一共有多少页。
                        val NumberPage =
                            response.select("font[color=#ff0000]")
                        page =
                            NumberPage[1].text().toInt()
                        if (page % 30 > 0) page =
                            page / 30 + 1 else page /= 30
                    }
                }

                //这是找每一页中的其余页面
                var processPlus = 16 / (page - 1)
                for (k in 2..page) {
                    tempProces += processPlus
                    process.postValue(tempProces)
                    var currentChildNode = true
                    var tiaozhuan: Document
                    while (currentChildNode) {
                        tiaozhuan = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0")
                            .timeout(20 * 1000)
                            .method(Connection.Method.GET)
                            .maxBodySize(0)
                            .followRedirects(false)
                            .data("show2", "1")
                            .data("yxid", "")
                            .data("sjd", i.toString())
                            .data("page", k.toString())
                            .data("Submit", "确定")
                            .followRedirects(true)
                            .post()
                        if (AnalysiRoom(tiaozhuan, i, false)) {
                            currentChildNode = false
                        }
                    }
                }
                i += 2
            }
            Log.d(TAG, "SpideSchool:爬取结束，准备上传 ");
        } catch (e: Exception) {
            // 这里填写超时逻辑
            Log.d(TAG, "SpideSchool:失败 " + e.toString());
            process.postValue(-1)
            return@liveData
        }
        //上传信息


        var prefList = roomList.subList(0, roomList.size / 2)
        var lastList = roomList.subList(roomList.size / 2, roomList.size)


        val prefUpLoadMsg = UploadRoomMsg(
            if (Dao.getQQ() != null) Dao.getQQ()!!.nickname else "匿名小可爱",
            gson.toJson(prefList)
        )
        prefUpLoadMsg.update("2ec49dadea", object : UpdateListener() {
            override fun done(p0: BmobException?) {
                if (p0 == null) {
                    process.postValue(99)
                } else {
                    process.postValue(-1)
                }
            }

        })
        val lastUpLoadMsg = UploadRoomMsg(
            if (Dao.getQQ() != null) Dao.getQQ()!!.nickname else "匿名小可爱",
            gson.toJson(lastList)
        )
        lastUpLoadMsg.update("lTM53338", object : UpdateListener() {
            override fun done(p0: BmobException?) {
                if (p0 == null) {
                    process.postValue(100)
                } else {
                    process.postValue(-1)
                }
            }

        })
    }


    /**
     * 处理教室
     *
     * @param document
     * @param i
     * @param liren
     * @return
     */
    fun AnalysiRoom(
        document: Document,
        i: Int,
        liren: Boolean
    ): Boolean {
        val classroom =
            if (liren) document.select("td[bgcolor=#FFFFFF][width=167]") else document.select("td[bgcolor=#FFFFFF][width=89]") //检索出来全部是教室名字
        val location =
            if (liren) document.select("td[bgcolor=#FFFFFF][width=148]") else document.select("td[bgcolor=#FFFFFF][width=111]") //检索出来地点
        val number =
            if (liren) document.select("td[bgcolor=#FFFFFF][width=75]") else document.select("td[bgcolor=#FFFFFF][width=75]")
        val xiaoqu =
            if (liren) document.select("td[bgcolor=#FFFFFF][width=101]") else document.select("td[bgcolor=#FFFFFF][width=101]")
        if (classroom.size == 0) return false
        for (j in classroom.indices) {

            if (!schoolBuilding.contains(location[j].text())) continue

            val room = EmptyRoomMirror(
                classroom[j].text(),
                location[j].text(),
                xiaoqu[j].text(),
                if (!number[j].text().equals("")) number[j].text() else "99",
                i.toString()
            )
            roomList.add(room)

        }
        return true
    }


}

class EmptyRoomMirror(
    var room: String,
    var location: String,
    var xiaoqu: String = "",
    var nums: String,
    var time: String = ""
) {
}