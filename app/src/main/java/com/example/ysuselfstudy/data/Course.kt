package com.example.ysuselfstudy.data

import org.litepal.crud.LitePalSupport

/**
 * @author  Ahyer
 * @date  2020/4/13 15:14
 * @version 1.0
 */
data class Course(
    var courseName: String = "",
    var teacher: String = "",
    var dayOfWeek: Int = 0,
    var beginNode: Int = 0,
    var continued: Int = 1,
    var position: String = "",
    var startWeek: Int = 0,
    var endWeek: Int = 0,
    var nature: String = "",
    var color: Int = 0,
    var isLabCourse: Boolean = false,
    var isOdd: Boolean = true,
    var isSingleDoubel: Boolean = false,
    var delete: Boolean = false
) : LitePalSupport() {
    fun clone(): Course {
        return Course(
            courseName,
            teacher,
            dayOfWeek,
            beginNode,
            1,
            position,
            startWeek,
            endWeek,
            nature,
            color,
            isLabCourse,
            isOdd,
            isSingleDoubel,
            delete
        )
    }

    constructor(course: Course) : this() {
        this.endWeek = course.endWeek
        this.courseName = course.courseName
        this.teacher = course.teacher
        this.dayOfWeek = course.dayOfWeek
        this.beginNode = course.beginNode
        this.continued = course.continued
        this.position = course.position
        this.startWeek = course.startWeek
        this.nature = course.nature
        this.color = course.color
        this.isLabCourse = course.isLabCourse
        this.isOdd = course.isOdd
        this.isSingleDoubel = course.isSingleDoubel
        this.delete = course.delete
    }

    override fun equals(other: Any?): Boolean {
        if (other is Course) {
            if (teacher.equals(other.teacher) && courseName.equals(other.courseName)
                && position.equals(other.position)) {
                return true
            }
        }
        return false
    }

    override fun toString(): String {
        return "名称" + courseName + "地点" + position
    }
}