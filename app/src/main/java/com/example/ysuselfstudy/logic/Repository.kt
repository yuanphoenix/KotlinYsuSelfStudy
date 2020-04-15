package com.example.ysuselfstudy.logic


import android.util.Log
import androidx.lifecycle.liveData
import com.example.ysuselfstudy.data.Course
import com.example.ysuselfstudy.network.EmptyRoomNetWork
import com.example.ysuselfstudy.network.OfficeNetWork
import com.example.ysuselfstudy.network.ServiceCreator
import kotlinx.coroutines.Dispatchers
import okhttp3.HttpUrl
import org.litepal.LitePal
import java.lang.Exception

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
            Dao.deleteAllCourse()
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
        //获取验证码图片
        val code_byteArray = OfficeNetWork.getCode()
        //识别验证码
        val code = OfficeNetWork.postCode(code_byteArray)
        //发送登录请求
        val result = OfficeNetWork.login(num, password, code)
        //这里已经知道了登录的结果，如果为 true ，那么获取一些GBKName等信息。
        emit(result)

    }

    /**
     *返回考试信息
     */
    fun getExam() {

    }

    /**
     * 返回成绩信息
     */

    fun getGrade() {

    }

}