package com.example.ysuselfstudy.data

import org.litepal.crud.LitePalSupport

/**
 * @author  Ahyer
 * @date  2020/4/10 10:35
 * @version 1.0
 */
data class User(
    var gbkName: String="",
    var number: String,
    var eduPassword: String="",
    var libraryPassword: String="",
    var todaySchoolPassword: String=""
) : LitePalSupport() {
}
