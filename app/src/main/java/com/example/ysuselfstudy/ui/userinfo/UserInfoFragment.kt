package com.example.ysuselfstudy.ui.userinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ysuselfstudy.MainViewModel

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.data.User
import com.example.ysuselfstudy.databinding.UserInfoFragmentBinding
import com.example.ysuselfstudy.logic.Dao
import kotlinx.android.synthetic.main.main_fragment.*
import org.litepal.LitePal

class UserInfoFragment : Fragment() {
    companion object {
        fun newInstance() = UserInfoFragment()
    }

    private lateinit var viewModel: UserInfoViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: UserInfoFragmentBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_info_fragment, container, false)
        navController = findNavController()
        binding.btnLogin.setOnClickListener {
            navController.navigate(R.id.loginFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.state.observe(
            viewLifecycleOwner,
            Observer { authenticationState ->

                when (authenticationState) {
                    false -> {
                        binding.btnLogin.visibility = View.VISIBLE
                        binding.userConstrainLayout.visibility = View.GONE
                    }
                    true -> {
                        binding.btnLogin.visibility = View.GONE
                        binding.userConstrainLayout.visibility = View.VISIBLE
                    }
                }
            })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)

    }

}
