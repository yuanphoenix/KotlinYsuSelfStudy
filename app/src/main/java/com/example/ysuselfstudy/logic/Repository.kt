package com.example.ysuselfstudy.logic


import android.util.Log
import androidx.lifecycle.liveData
import com.example.ysuselfstudy.data.Course
import com.example.ysuselfstudy.data.Exam
import com.example.ysuselfstudy.data.Information
import com.example.ysuselfstudy.network.EmptyRoomNetWork
import com.example.ysuselfstudy.network.OfficeNetWork
import kotlinx.coroutines.Dispatchers
import org.jsoup.select.Elements

/**
 * @author  Ahyer
 * @date  2020/4/11 11:18
 * @version 1.0
 */
object Repository {

    private val TAG = "Repository"

    /**
     * 返回当天的空教室
     */
    fun getRoom(query: String) = liveData(Dispatchers.IO) {
        //在这里应该写判返回值空逻辑
        var result = try {
            val room = EmptyRoomNetWork.searchRoom(query)
            Dao.saveRoom(room)
            Result.success(room) //复制给了result
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
        emit(result)
    }


    /**
     *返回本周的课程
     */
    fun getTimeStable() = liveData(Dispatchers.IO) {
        var result = ArrayList<Course>()
        if (Dao.isCourseEmpty()) {
            val documnet = OfficeNetWork.getCourse()
            CourseAnalysis.analysisCourse(documnet)
        }
        result = Dao.getWeekClass()
        emit(result)
    }

    /**
     * 返回是否登录成功
     */

    fun getLoginState(num: String, password: String) = liveData(Dispatchers.IO) {
        var result = false;
        try {
            //获取验证码图片
            val code_byteArray = OfficeNetWork.getCode()
            //识别验证码
            val code = OfficeNetWork.postCode(code_byteArray)
            //发送登录请求
            result = OfficeNetWork.login(num, password, code)
            //这里已经知道了登录的结果，如果为 true ，那么获取一些GBKName等信息。
        } catch (e: Exception) {
            result = false
        }
        emit(result)

    }

    /**
     *返回考试信息
     */
    fun getExam() = liveData(Dispatchers.IO) {
        val document = OfficeNetWork.getExam()
        val examList = ArrayList<Exam>()
        if (document.body().text().length == 0) emit(examList)

        //获取了所有爬取的信息。
        val elements: Elements = document.select("td")


        val size = elements.size

        var i = 11
        while (i < size) {
            val time = elements[i].text()
            if (time != "") {
                val examName = elements[i - 2].text()
                val location = elements[i + 1].text()
                val number = elements[i + 3].text()
                val examBean = Exam(examName, time, location, number)
                examList.add(examBean)
            } else {
                //出考试时间的一定在最前面，一旦没有发现考试时间，那么就停止。
                break
            }
            i += 8
        }

        emit(examList)

    }

    /**
     * 返回成绩信息
     */

    fun getGrade() {

    }

    fun getInformation() = liveData(Dispatchers.IO) {
        try {
            var list = OfficeNetWork.getInformation() ?: null
            emit(list)
        } catch (e: Exception) {
            emit(null)
        }
    }


}