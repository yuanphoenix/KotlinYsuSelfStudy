package com.example.ysuselfstudy.logic.qqlogin

import com.tencent.open.utils.HttpUtils
import com.tencent.tauth.IRequestListener
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.net.MalformedURLException
import java.net.SocketTimeoutException

/**
 * @author  Ahyer
 * @date  2020/4/16 17:28
 * @version 1.0
 */
class BaseApiListener : IRequestListener
{
    override fun onJSONException(p0: JSONException?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUnknowException(p0: Exception?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onComplete(p0: JSONObject?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNetworkUnavailableException(p0: HttpUtils.NetworkUnavailableException?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMalformedURLException(p0: MalformedURLException?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSocketTimeoutException(p0: SocketTimeoutException?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onIOException(p0: IOException?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onHttpStatusException(p0: HttpUtils.HttpStatusException?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectTimeoutException(p0: ConnectTimeoutException?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}