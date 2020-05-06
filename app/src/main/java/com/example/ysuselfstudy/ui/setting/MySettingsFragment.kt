package com.example.ysuselfstudy.ui.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

import com.example.ysuselfstudy.MainViewModel
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.logic.Dao
import com.example.ysuselfstudy.logic.qqlogin.BaseUiListener

/**
 * A simple [Fragment] subclass.
 */
class MySettingsFragment : PreferenceFragmentCompat() {
    lateinit var mainViewModel: MainViewModel
    private val TAG = "MySettingsFragment"
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_setting, rootKey)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        val logout: Preference? = findPreference("logout")
        logout?.setOnPreferenceClickListener {
            mainViewModel.logoutQQ()
            true
        }

        val share: Preference? = findPreference("share")
        share?.setOnPreferenceClickListener {
            BaseUiListener().onClickAppShare(requireActivity())
            true
        }

        val feedback: Preference? = findPreference("feedback")
        feedback?.setOnPreferenceClickListener {

            var intent = Intent()
            intent.setAction(Intent.ACTION_VIEW)

            var user = Dao.getQQ()
            var uri: Uri
            if (user != null) {
                var tempurl = user.image.replace("&", "%26")
                uri =
                    Uri.parse("https://support.qq.com/product/115180?avatar=${tempurl}&nickname=${user.nickname}&openid=${user.openID}")
            } else
                uri = Uri.parse("https://support.qq.com/product/115180?")

            intent.setData(uri)

            startActivity(intent)
            true
        }
    }
}
