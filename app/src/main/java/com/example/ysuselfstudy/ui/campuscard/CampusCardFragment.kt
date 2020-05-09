package com.example.ysuselfstudy.ui.campuscard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.databinding.CampusCardFragmentBinding

class CampusCardFragment : Fragment() {
    private val TAG = "CampusCardFragment"

    companion object {
        fun newInstance() =
            CampusCardFragment()
    }

    private lateinit var viewModel: CampusCardViewModel
    private lateinit var binding: CampusCardFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.campus_card_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CampusCardViewModel::class.java)
        //如果没有登录，那么先登录

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.userNumber.text.toString(),
                binding.officePassword.text.toString()
            )
        }

        viewModel.loginRoute()

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            when (it) {
                CampusCardViewModel.TodayAuthenticationState.AUTHENTICATED -> showUi()
            }
        })
        viewModel.loginState.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                showUi()
                binding.textSurplus.text = "￥${it.remining}"
            } else {
                viewModel.authenticationState.value =
                    CampusCardViewModel.TodayAuthenticationState.UNAUTHENTICATED
            }
        })


    }

    private fun showUi() {
        binding.loginToday.visibility = View.GONE
        binding.cardSurplus.visibility = View.VISIBLE

    }

}
