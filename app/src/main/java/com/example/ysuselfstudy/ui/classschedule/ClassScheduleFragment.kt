package com.example.ysuselfstudy.ui.classschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.databinding.ClassScheduleFragmentBinding

class ClassScheduleFragment : Fragment() {
    companion object {
        fun newInstance() = ClassScheduleFragment()
    }

    private lateinit var viewModel: ClassScheduleViewModel
    private lateinit var binding: ClassScheduleFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.class_schedule_fragment, container, false)
        var controller = findNavController()




        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClassScheduleViewModel::class.java)

    }

}
