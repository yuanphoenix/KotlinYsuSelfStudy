package com.example.ysuselfstudy.logic

import android.os.Build
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.data.Course
import com.example.ysuselfstudy.data.EmptyRoom
import com.example.ysuselfstudy.data.QQ
import com.example.ysuselfstudy.data.User
import org.litepal.LitePal

/**
 * @author  Ahyer
 * @date  2020/4/13 15:05
 * @version 1.0
 */
object Dao {
    /**
     * 保存学生的个人信息
     */
    fun saveStu(num: String, password: String, gbkName: String) {
        val user = User(number = num, eduPassword = password, gbkName = gbkName)
        if (LitePal.count(User::class.java) > 0) {
            if (!LitePal.findFirst(User::class.java).number.equals(num)) {
                LitePal.deleteAll(User::class.java)
            } else return
        }
        user.save()
    }

    /**
     * 获取唯一的用户
     */
    fun getStu(): User = LitePal.findFirst(User::class.java)

    /**
     * 删除当天所有的空教室
     */
    fun deleteRoom() = LitePal.deleteAllAsync(EmptyRoom::class.java)


    /**
     * 获取教室
     */
    fun saveRoom(list: List<EmptyRoom>) = LitePal.saveAll(list)


    /**
     * 获取符合条件的教室
     */
    fun getRoom(conditon: String) {

    }

    /**
     * 保存本学期所有的课程
     */
    fun saveAllCourse(list: List<Course>) = LitePal.saveAll(list)


    /**
     * 删除本学期所有的课
     */
    fun deleteAllCourse() = LitePal.deleteAll(Course::class.java)


    /**
     * 查看课程表的数据库是否为空
     */
    fun isCourseEmpty(): Boolean = LitePal.count(Course::class.java) == 0


    fun keepQQLogin(): Boolean {
        if (LitePal.count(QQ::class.java) != 0) {
            val qqUser = LitePal.findFirst(QQ::class.java)
            YsuSelfStudyApplication.tencent.setAccessToken(qqUser.accessToken, qqUser.expires)
            YsuSelfStudyApplication.tencent.openId = qqUser.openID
            return true
        }
        return false
    }

    fun deleteQQ(){
        LitePal.deleteAll(QQ::class.java)
    }

    /**
     * 返回本周的课程
     */
    fun getWeekClass(): ArrayList<Course> {
        val list = ArrayList<Course>()//声明一个样表
        repeat(84) { list.add(Course()) }
        //这一步有问题，不会是这样的。
        val nowWeek = getWeek()
        val weekList = ArrayList<Course>()
        var findAll = LitePal.findAll(Course::class.java)
        //如果不是本周的，就取消
        for (course in findAll) {
            if (nowWeek < course.startWeek || nowWeek > course.endWeek) {
                continue
            }
            if (course.isSingleDoubel) {
                if (nowWeek % 2 == 0 && course.isOdd || nowWeek % 2 == 1 && !course.isOdd) {
                    continue
                }
            }
            weekList.add(course)
        }


        //在样表中分离各个课程
        for (course in weekList) {
            var temp = 1
            while (temp <= course.continued) {
                list[(course.beginNode - 1) * 7 + course.dayOfWeek - 1 + (temp - 1) * 7] =
                    course.clone()
                temp++
            }
        }

        //合并课程，设置删除标记位
        for (i in 83 downTo 0) {
            if (list[i].dayOfWeek != 0) {
                if (i - 7 >= 0) {
                    if (list[i].equals(list[i - 7])) {
                        list[i].delete = true
                        list[i - 7].continued += list[i].continued
                    }
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.removeIf { t: Course -> t.delete == true }
            return list
        } else {
            var result = ArrayList<Course>()
            for (course in list) {
                if (!course.delete) {
                    result.add(course)
                }
            }
            return result

        }


    }
}