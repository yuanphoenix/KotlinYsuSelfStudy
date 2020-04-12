package com.example.ysuselfstudy.data

import org.litepal.crud.LitePalSupport

/**
 * @author  Ahyer
 * @date  2020/4/10 17:30
 * @version 1.0
 */
data class EmptyRoom(
    var room: String,
    var location: String,
    var xiaoqu: String,
    var nums: String,
    var time: String
) : LitePalSupport() {
}