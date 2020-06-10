package com.example.ysuselfstudy.data

import cn.bmob.v3.BmobObject

data class InformCollect(
    val url: String,
    val title: String,
    val openID: String,
    val time: String
) : BmobObject() {
}