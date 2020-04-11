package com.example.ysuselfstudy.adapter

import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import com.example.ysuselfstudy.R
import razerdp.basepopup.BasePopupWindow

/**
 * @author  Ahyer
 * @date  2020/4/11 21:08
 * @version 1.0
 */
class LoginPop(var context: Fragment) : BasePopupWindow(context) {
init {
    popupGravity = Gravity.CENTER
}
    override fun onCreateContentView(): View {
        return createPopupById(R.layout.login_office)
    }


}