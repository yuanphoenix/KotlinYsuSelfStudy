package com.example.ysuselfstudy.data

import cn.bmob.v3.BmobObject

/**
 * @author  Ahyer
 * @date  2020/5/5 10:13
 * @version 1.0
 */
data class UploadRoomMsg(var nick_name: String, var room_json: String) : BmobObject() {
}