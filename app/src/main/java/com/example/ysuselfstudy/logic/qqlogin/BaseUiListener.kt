package com.example.ysuselfstudy.logic.qqlogin

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.data.QQ
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
    private val TAG = "BaseUiListener"
    private lateinit var onSuccessListener: OnSuccessListener

    override fun onComplete(response: Any?) {
        val obj = response as JSONObject
        Log.d(TAG, "onComplete: " + obj.toString());
        val openID = obj.getString("openid")
        val accessToken = obj.getString("access_token")
        val expires = obj.getString("expires_in")

        //不写这两句不行，tencent没自动保存？
        YsuSelfStudyApplication.tencent.openId = openID
        YsuSelfStudyApplication.tencent.setAccessToken(accessToken, expires)

        val qqToken: QQToken = YsuSelfStudyApplication.tencent.getQQToken()

        val mUserInfo = UserInfo(YsuSelfStudyApplication.context, qqToken)
        mUserInfo.getUserInfo(object : IUiListener {
            override fun onComplete(p0: Any?) {
                //成功获取到用户的信息

                val qqUser = p0 as JSONObject
                val nickname: String = qqUser.getString("nickname")
                val touxiang: String = qqUser.getString("figureurl_qq_2")
                QQ(accessToken, openID, expires, touxiang, nickname).save()
                if (onSuccessListener != null)
                    onSuccessListener.changeImageView()

                //这里如何发送消息给Activity？
            }

            override fun onCancel() {

            }

            override fun onError(p0: UiError?) {

            }

        })


    }

    override fun onCancel() {
        Log.d(TAG, "onCancel: 取消登录");
    }

    override fun onError(p0: UiError?) {
        Log.d(TAG, "onError: " + p0.toString());
    }

    interface OnSuccessListener {
        fun changeImageView()
    }

    fun setOnSuccessListener(listener: OnSuccessListener) {
        this.onSuccessListener = listener
    }

}