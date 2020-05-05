package com.example.ysuselfstudy.ui.exam

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ysuselfstudy.MainViewModel

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.adapter.ExamAdapter
import com.example.ysuselfstudy.data.Exam
import com.example.ysuselfstudy.data.Grade
import com.example.ysuselfstudy.databinding.ExamFragmentBinding

class ExamFragment : Fragment() {

    companion object {
        fun newInstance() = ExamFragment()
    }



    private lateinit var viewModel: ExamViewModel
    private lateinit var mainModel: MainViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var mData = ArrayList<Exam>()
    private lateinit var binding: ExamFragmentBinding
    private lateinit var adapter: ExamAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.exam_fragment, container, false)
        linearLayoutManager = LinearLayoutManager(YsuSelfStudyApplication.context)
        binding.examRecycler.layoutManager = linearLayoutManager
        adapter = ExamAdapter(mData)
        binding.examRecycler.adapter = adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExamViewModel::class.java)

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
        binding.progressBar.show()
        viewModel.getExam()
        viewModel.list.observe(viewLifecycleOwner, Observer {
            mData.clear()
            binding.progressBar.hide()
            it?.let { it1 -> mData.addAll(it1) }
            adapter.notifyDataSetChanged()
        })
    }

}
