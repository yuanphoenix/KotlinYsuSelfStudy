package com.example.ysuselfstudy.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ysuselfstudy.MainViewModel
import com.example.ysuselfstudy.R
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
            hideInput()
            mainViewModel.authenticate(
                binding.userNumber.text.toString(), binding.officePassword.text.toString()

            )
        }

        mainViewModel.state.observe(viewLifecycleOwner, Observer {
            if (it) {
                mainViewModel.authenticationState.value =
                    MainViewModel.AuthenticationState.AUTHENTICATED
                navController.popBackStack()
            } else {
                showErrorMessage()
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            mainViewModel.refuseAuthentication()
            navController.popBackStack()

        }


        //登录界面自动填充
        if (LitePal.count(User::class.java) > 0) {
            val user = LitePal.findFirst(User::class.java)
            binding.userNumber.setText(user.number)
            binding.officePassword.setText(user.eduPassword)
        }
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

