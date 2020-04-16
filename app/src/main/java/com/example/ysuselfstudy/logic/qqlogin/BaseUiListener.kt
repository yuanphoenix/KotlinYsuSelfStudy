package com.example.ysuselfstudy.logic.qqlogin

import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.tencent.connect.UserInfo
import com.tencent.connect.auth.QQToken
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError
import org.json.JSONObject

/**
 * @author  Ahyer
 * @date  2020/4/16 17:27
 * @version 1.0
 */
class BaseUiListener : IUiListener {
    override fun onComplete(response: Any?) {
        val obj = response as JSONObject
        val openID = obj.getString("openid")
        val accessToken = obj.getString("access_token")
        val expires = obj.getString("expires_in")

     //   YsuSelfStudyApplication.tencent.setOpenId(openID)
    //    YsuSelfStudyApplication.tencent.setAccessToken(accessToken, expires) //这里应该持久化保存

        //     DateBaseManager.deleteQQ()

//        val qqToken: QQToken = YsuSelfStudyApplication.tencent.getQQToken()
//        val mUserInfo = UserInfo(YsuSelfStudyApplication.context, qqToken)
//        mUserInfo.getUserInfo(BaseUiListener())

    }

    override fun onCancel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(p0: UiError?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}