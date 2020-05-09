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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ysuselfstudy.MainViewModel

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.YsuSelfStudyApplication
import com.example.ysuselfstudy.adapter.GradeAdapter
import com.example.ysuselfstudy.data.Grade
import com.example.ysuselfstudy.databinding.GradeFragmentBinding

class GradeFragment : Fragment() {
    private val TAG = "GradeFragment"

    companion object {
        fun newInstance() = GradeFragment()
    }

    private lateinit var viewModel: GradeViewModel
    private lateinit var mainModel: MainViewModel
    private lateinit var binding: GradeFragmentBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var mData = ArrayList<Grade>()
    private lateinit var adapter: GradeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.grade_fragment, container, false)
        linearLayoutManager = LinearLayoutManager(YsuSelfStudyApplication.context)
        binding.gradeRecycler.layoutManager = linearLayoutManager
        adapter = GradeAdapter(mData)
        binding.gradeRecycler.adapter = adapter
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
        binding.progressBar.show()
        viewModel.getGrade()
        viewModel.list.observe(viewLifecycleOwner, Observer {

            mData.clear()
            binding.progressBar.hide()
            mData.addAll(it)
            adapter.notifyDataSetChanged()
        })

        binding.gradeRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                var firstItemPositon = linearLayoutManager.findFirstVisibleItemPosition()
                binding.currentSemester.text =
                    "${mData[firstItemPositon].date}第${mData[firstItemPositon].semester}学期"
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }
        })

    }

}
