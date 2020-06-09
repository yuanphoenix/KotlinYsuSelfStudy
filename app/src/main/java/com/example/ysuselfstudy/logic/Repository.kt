package com.example.ysuselfstudy.logic


import android.util.Log
import androidx.lifecycle.liveData
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.data.*
import com.example.ysuselfstudy.network.EmptyRoomNetWork
import com.example.ysuselfstudy.network.OfficeNetWork
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.bugly.crashreport.CrashReport
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
     * 返回当天的空教室，已被废弃
     */
    @Deprecated("Api已换")
    fun getRoom(query: String) = liveData(Dispatchers.IO) {
        if (!Dao.isRoomEmpty()) {
            emit(null)
            return@liveData
        }
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
     * 测试空教室
     */
    fun getEmptyRoom() = liveData(Dispatchers.IO) {
        if (!Dao.isRoomEmpty()) {
            emit(null)
            return@liveData
        }
        emit(EmptyRoomNetWork.getEmptyRoom())
    }

    /**
     * 矫正周数
     */
    fun makeCorrect() = liveData(Dispatchers.IO) {
        var makecorrect = MakeCorrectWeek()
        try {
            val json = OfficeNetWork.makeCorrectWeek()
            var type = object : TypeToken<MakeCorrectWeek>() {}.type

            makecorrect = Gson().fromJson(json, type)
            getBeginDate(makecorrect.weekOfTerm.toInt())
            emit("Hello")
        } catch (e: Exception) {
            emit(null)
            return@liveData
        }

    }


    /**
     *返回本周的课程
     *如果本地有数据，那么就加载本地数据
     * 否则联网获得数据
     */
    fun getTimeStable(login: Boolean?, week: Int = -1): Any? = liveData(Dispatchers.IO) {

        var result = ArrayList<Course>()
        if (login != null && !login) {
            emit(null)
            return@liveData
        }
        if (Dao.isCourseEmpty()) {
            val documnet = OfficeNetWork.getCourse()
            try {
                var isAnalysis = CourseAnalysis.analysisCourse(documnet)
                if (isAnalysis == null) emit(0)
            } catch (thr: Throwable) {
                Log.d(TAG, "getTimeStable: " + thr.toString());
                CrashReport.postCatchedException(thr)
            }
        }
        result = Dao.getWeekClass(week)
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
        if (document.body().text().length == 0) emit(null)

        //获取了所有爬取的信息。
        val elements: Elements = document.select("td")
        val size = elements.size
        if (size == 0) {
            emit(null)
            return@liveData
        }
        var i = 11
        while (i < size) {
            val time = elements[i].text()
            if (time != "") {
                val examName = elements[i - 2].text()
                val location = elements[i + 1].text()
                val number = elements[i + 3].text()
                val examBean = Exam(
                    examName,
                    time,
                    location,
                    Clock.isExistEvent(YsuSelfStudyApplication.context, examName) ,
                    number
                )
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
    fun getGrade() = liveData(Dispatchers.IO) {
        var document = OfficeNetWork.getGrade()
        var elements = document.select("table[width=950] tr[bgcolor=#D0E8FF] td")
        var gradeList = ArrayList<Grade>()
        var i = elements.size - 15
        var tempMsg = ""
        while (i >= 0) {
            val date = elements.get(i).text()
            val semester = elements.get(i + 1).text()
            val project = elements.get(i + 3).text()
            val credit = elements.get(i + 7).text()
            val grade = elements.get(i + 9).text()
            val resit = elements.get(i + 10).text()
            val degree = elements.get(i + 14).text()
            var temp = Grade(project, date, semester, credit, grade, resit = false, degree = false)
            if (!resit.equals("")) {
                temp.grade = resit
                temp.resit = true
            }
            if (!degree.equals("")) temp.degree = true
            if (!tempMsg.equals(semester)) {
                gradeList.add(Grade(date = date, semester = semester))
                tempMsg = semester
            }
            gradeList.add(temp)
            i -= 15
        }
        gradeList.removeAt(0)
        emit(gradeList)
    }

    /**
     * 返回公告列表
     */
    fun getInformation() = liveData(Dispatchers.IO) {
        try {
            var list = OfficeNetWork.getInformation() ?: null
            emit(list)
        } catch (e: Exception) {
            emit(null)
        }
    }

    /**
     * 返回公告详情
     */
    fun getDetailInformation(url: String) = liveData(Dispatchers.IO) {
        try {
            var html = OfficeNetWork.getDetailInformation(url)
            emit(html)
        } catch (e: Exception) {
            emit(null)
        }
    }

    /**
     * 获取余额
     */
    fun getCardSurplus(user: User) = liveData(Dispatchers.IO) {

        try {

            var surplus: Root

            var json = OfficeNetWork.getCardSurplus(user)

            var type = object : TypeToken<Root>() {}.type
            try {
                surplus = Gson().fromJson(json, type)
            } catch (e: Exception) {
                emit(null)//密码不对
                return@liveData
            }

            if (Dao.isStuEmpty()) user.save() else {
                var savedUser = Dao.getStu()
                if (savedUser.number == user.number) {
                    savedUser.todaySchoolPassword = user.todaySchoolPassword
                    savedUser.save()
                } else {
                    savedUser.delete()
                    user.save()
                }

            }
            emit(surplus)
        } catch (e: Exception) {
            emit(null)
        }
    }

    fun getGPA() = liveData(Dispatchers.IO) {
        val document = OfficeNetWork.getGPA()
        var info = document.select("tr[bgcolor=#D0E8FF]")
        if (info.size > 2) {
            info.removeAt(0)
        }
        info = info.select("td")
        val averageGPANormal = info[3].text().toDouble() //必修课平均绩点（正考）

        val averageGPAMost = info[4].text().toDouble() //必修课平均绩点（最高）

        val averageGPAIncludeOptional = info[5].text().toDouble() //必修课平均绩点（最高含选修）

        val degreeCourseGPA = info[10].text().toDouble() //学位课绩点

        val gpa = GPA(averageGPANormal, averageGPAMost, averageGPAIncludeOptional, degreeCourseGPA)
        emit(gpa)
    }

    /**
     * 返回必应首页
     */
    fun getBiying() = liveData(Dispatchers.IO) {
        if (Dao.isBiying()) {
            try {
                val temp = EmptyRoomNetWork.SearchForBiYing()
                emit(temp)
            } catch (e: Exception) {
                emit("")
            }
        } else {
            emit(Dao.getBiying())
        }

    }

}