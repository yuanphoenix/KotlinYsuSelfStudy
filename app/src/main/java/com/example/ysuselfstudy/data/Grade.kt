package com.example.ysuselfstudy.data

/**
 * @author  Ahyer
 * @date  2020/4/20 10:01
 * @version 1.0
 */

data class Grade(
    var project: String = "",
    var date: String = "",
    var semester: String = "",
    var credit: String = "",
    var grade: String = "",
    var resit: Boolean = false,
    var degree: Boolean = false
) {
}