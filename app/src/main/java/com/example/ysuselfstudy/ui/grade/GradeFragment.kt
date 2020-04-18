package com.example.ysuselfstudy.ui.grade

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ysuselfstudy.MainViewModel

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.databinding.GradeFragmentBinding

class GradeFragment : Fragment() {
private val TAG ="GradeFragment"
    companion object {
        fun newInstance() = GradeFragment()
    }

    private lateinit var viewModel: GradeViewModel
    private lateinit var mainModel: MainViewModel
    private lateinit var binding: GradeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.grade_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GradeViewModel::class.java)
        mainModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.authen = MainViewModel.AuthenticationState.UNAUTHENTICATED
        binding.viewmodel = mainModel
        binding.lifecycleOwner = this
        val navController = findNavController()
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
        Log.d(TAG, "showUi: 你好");
    }

}
