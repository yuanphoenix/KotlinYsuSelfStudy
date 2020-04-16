package com.example.ysuselfstudy.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ysuselfstudy.MainViewModel

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.data.User
import com.example.ysuselfstudy.databinding.LoginFragmentBinding
import com.google.android.material.snackbar.Snackbar
import org.litepal.LitePal

class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: LoginFragmentBinding
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        navController = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.btnLogin.setOnClickListener {
            mainViewModel.authenticate(
                binding.userNumber.text.toString(), binding.officePassword.text.toString()
            )
        }

        mainViewModel.state.observe(viewLifecycleOwner, Observer {
            if (it) {
                navController.popBackStack()
            } else {
                showErrorMessage()
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            mainViewModel.refuseAuthentication()
            navController.popBackStack()

        }


        if (LitePal.count(User::class.java) > 0) {
            val user = LitePal.findFirst(User::class.java)
            binding.userNumber.setText(user.number)
            binding.officePassword.setText(user.eduPassword)
        }
    }


    private fun showErrorMessage() {
        Snackbar.make(this.view!!, "登陆失败", Snackbar.LENGTH_SHORT).show()
    }
}

