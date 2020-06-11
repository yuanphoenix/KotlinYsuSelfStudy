package com.example.ysuselfstudy.ui.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

import com.example.ysuselfstudy.MainViewModel
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.logic.Dao
import com.example.ysuselfstudy.logic.getWeek
import com.example.ysuselfstudy.logic.qqlogin.BaseUiListener
import com.example.ysuselfstudy.logic.showToast

/**
 * A simple [Fragment] subclass.
 */
class MySettingsFragment : PreferenceFragmentCompat() {
    lateinit var mainViewModel: MainViewModel
    private val TAG = "MySettingsFragment"
    lateinit var navController: NavController
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_setting, rootKey)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        navController = findNavController()
        mainViewModel.correct.observe(requireActivity(), Observer {
            if (it.isNullOrEmpty()) {
                "矫正失败".showToast()
            } else {
                "矫正成功，第${getWeek()}周,重启软件生效".showToast()
            }
        })

        val logout: Preference? = findPreference("logout")
        logout?.setOnPreferenceClickListener {
            mainViewModel.logoutQQ()
            YsuSelfStudyApplication.myinform.clear()
            true
        }

        val share: Preference? = findPreference("share")
        share?.setOnPreferenceClickListener {
            BaseUiListener().onClickAppShare(requireActivity())
            true
        }

        val makeCorrection: Preference? = findPreference("makeCorrection")
        makeCorrection?.setOnPreferenceClickListener {
            if (Dao.isStuEmpty() || Dao.getStu().todaySchoolPassword.equals("")) {
                "请先查一次一卡通余额".showToast()
            } else {
                mainViewModel.makeCorrect()
            }
            true
        }

        val feedback: Preference? = findPreference("feedback")
        feedback?.setOnPreferenceClickListener {
            navController.navigate(R.id.feedBack)
            true
        }

        val password: Preference? = findPreference("charge_password")
        password?.setOnPreferenceClickListener {
            if (!Dao.isStuEmpty()) {
                PasswordChangeFragment().show(getParentFragmentManager(), "Hello")
            } else {
                "没有密码".showToast()
            }

            true
        }

    }
}
