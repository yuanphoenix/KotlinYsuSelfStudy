package com.example.ysuselfstudy.ui.grade

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ysuselfstudy.MainViewModel
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.databinding.GPAFragmentBinding
import com.example.ysuselfstudy.logic.showToast

class GPAFragment : Fragment() {

    companion object {
        fun newInstance() = GPAFragment()
    }

    private lateinit var viewModel: GPAViewModel
    private lateinit var binding: GPAFragmentBinding
    private lateinit var mainModel:MainViewModel
    private lateinit var navController:NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= DataBindingUtil.inflate(inflater, R.layout.g_p_a_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GPAViewModel::class.java)
        mainModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.authen = MainViewModel.AuthenticationState.UNAUTHENTICATED
        binding.viewmodel = mainModel
        binding.lifecycleOwner = this
        navController = findNavController()
        binding.classLoginBtn.setOnClickListener { navController.navigate(R.id.loginFragment) }
        mainModel.state.observe(
            viewLifecycleOwner,
            Observer { authenticationState ->
                when (authenticationState) {
                    true -> {
                        showUi()
                    }
                }
            })

    }

    private fun showUi() {
        viewModel.getGPA()
        viewModel.GPA.observe(viewLifecycleOwner, Observer {
            binding.bean = it
        })
    }

}