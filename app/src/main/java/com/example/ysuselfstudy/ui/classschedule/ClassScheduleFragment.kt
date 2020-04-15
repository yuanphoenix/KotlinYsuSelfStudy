package com.example.ysuselfstudy.ui.classschedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ysuselfstudy.MainViewModel
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.adapter.CourseAdapter
import com.example.ysuselfstudy.data.Course
import com.example.ysuselfstudy.databinding.ClassScheduleFragmentBinding

class ClassScheduleFragment : Fragment() {
    companion object {
        fun newInstance() = ClassScheduleFragment()

    }

    private val TAG = "ClassScheduleFragment"
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: ClassScheduleViewModel
    private lateinit var binding: ClassScheduleFragmentBinding
    private lateinit var navController: NavController
    private lateinit var adapter: CourseAdapter
    private var mData = ArrayList<Course>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.class_schedule_fragment, container, false)
        navController = findNavController()
        binding.classLoginBtn.setOnClickListener {
            navController.navigate(R.id.loginFragment)
        }
        var layoutManager = StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL)
        binding.schedule.layoutManager = layoutManager
        adapter = CourseAdapter(mData)
        binding.schedule.adapter = adapter


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClassScheduleViewModel::class.java)//本地的ViewModel
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mainViewModel.authenticationState.observe(
            this.viewLifecycleOwner,
            Observer { authenticationState ->

                when (authenticationState) {
                    MainViewModel.AuthenticationState.UNAUTHENTICATED -> {
                        binding.classLoginBtn.visibility = View.VISIBLE
                        binding.classConstraintLayout.visibility = View.GONE
                    }
                    MainViewModel.AuthenticationState.AUTHENTICATED -> {
                        binding.classLoginBtn.visibility = View.GONE
                        binding.classConstraintLayout.visibility = View.VISIBLE
                        showUi()
                    }
                }
            })

        viewModel.nowWeekCourse.observe(this.viewLifecycleOwner, Observer { result ->
            mData.clear()
            mData.addAll(result)
            adapter.notifyDataSetChanged()
        })


    }

    private fun showUi() {
        viewModel.getCourse()
    }

}
