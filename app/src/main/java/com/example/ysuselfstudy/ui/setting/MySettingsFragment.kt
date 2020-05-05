package com.example.ysuselfstudy.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.ysuselfstudy.MainViewModel
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.logic.Dao
import com.example.ysuselfstudy.logic.qqlogin.BaseUiListener

/**
 * A simple [Fragment] subclass.
 */
class MySettingsFragment : PreferenceFragmentCompat() {
    lateinit var mainViewModel: MainViewModel
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
    }
}
