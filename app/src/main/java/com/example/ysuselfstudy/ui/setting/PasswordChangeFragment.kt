package com.example.ysuselfstudy.ui.setting

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.data.User
import com.example.ysuselfstudy.databinding.PasswordChargeLayoutBinding
import com.example.ysuselfstudy.logic.Dao
import org.litepal.LitePal


class PasswordChangeFragment() : DialogFragment() {
    lateinit var binding: PasswordChargeLayoutBinding
    private val TAG = "PasswordChangeFragment"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = requireActivity()
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view: View = inflater.inflate(R.layout.password_charge_layout, null)
        binding = DataBindingUtil.bind(view)!!
        binding.bean = Dao.getStu()
        builder.setView(view)
            .setPositiveButton("确定") { dialog, which ->
                binding.bean!!.save()
            }.setNegativeButton("取消", null)
        val dialog = builder.create()
        dialog.window!!.setWindowAnimations(R.style.Animation_Design_BottomSheetDialog)
        return dialog


    }
}