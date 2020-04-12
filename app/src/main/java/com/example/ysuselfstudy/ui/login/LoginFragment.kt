package com.example.ysuselfstudy.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.data.User
import com.example.ysuselfstudy.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding

    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.viewmodel = viewModel
        binding.btnLogin.setOnClickListener {
            var num = binding.userNumber.text.toString()
            var password = binding.officePassword.text.toString()

            val user = User(number = num, eduPassword = password)
            viewModel.getLogin(user)
        }
        navController = findNavController()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.observe(requireActivity(), Observer { result ->
            if (viewModel.state.value.equals("success")) {
                Log.d(TAG, "onActivityCreated:成功 ");
                //应用全局设为true
                YsuSelfStudyApplication.isLogin = true
                navController.popBackStack()
            } else {
                Log.d(TAG, "onActivityCreated:登陆失败 ");
            }

        })
    }

}
