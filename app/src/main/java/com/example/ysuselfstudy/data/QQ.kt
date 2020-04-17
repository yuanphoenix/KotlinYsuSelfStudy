package com.example.ysuselfstudy.data

import com.example.ysuselfstudy.logic.Dao
import org.litepal.crud.LitePalSupport

/**
 * @author  Ahyer
 * @date  2020/4/17 10:14
 * @version 1.0
 */
data class QQ(
    var accessToken: String,
    var openID: String,
    var expires: String,
    var image: String,
    var nickname: String
) : LitePalSupport() {

    override fun save(): Boolean {
        if (this.isSaved) Dao.deleteQQ()
        return super.save()
    }
}