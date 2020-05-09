package com.example.ysuselfstudy.ui.campuscard

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.databinding.CampusCardFragmentBinding
import com.google.android.material.snackbar.Snackbar

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
            hideInput()
        }

        viewModel.loginRoute()

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            when (it) {
                CampusCardViewModel.TodayAuthenticationState.AUTHENTICATED -> showUi()
                CampusCardViewModel.TodayAuthenticationState.INVALID_AUTHENTICATION -> {
                    binding.cardSurplus.visibility = View.GONE
                    binding.loginToday.visibility = View.VISIBLE
                    showErrorMessage()
                }
            }
        })
        viewModel.loginState.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                showUi()
                binding.textSurplus.text = "￥${it.remining}"
            } else {
                viewModel.authenticationState.value =
                    CampusCardViewModel.TodayAuthenticationState.INVALID_AUTHENTICATION
            }
        })


    }

    private fun showUi() {
        binding.loginToday.visibility = View.GONE
        binding.cardSurplus.visibility = View.VISIBLE

    }


    private fun showErrorMessage() {
        Snackbar.make(this.requireView(), "登陆失败", Snackbar.LENGTH_SHORT).show()
    }

    protected fun hideInput() {


        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v: View = requireActivity().window.peekDecorView()
        if (null != v) {
            imm!!.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}
