package com.example.ysuselfstudy.ui.grade

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.adapter.GradeVPAdapter
import com.example.ysuselfstudy.databinding.GradeHostFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator

class GradeHostFragment : Fragment() {
    private lateinit var binding: GradeHostFragmentBinding
    lateinit var viewPagerAdapter: FragmentStateAdapter

    companion object {
        fun newInstance() = GradeHostFragment()
    }

    private lateinit var viewModel: GradeHostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.grade_host_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GradeHostViewModel::class.java)




        viewPagerAdapter = GradeVPAdapter(this)
        binding.gradeHost.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.gradeHost) { tab, position ->
            tab.text = if (position == 0) "成绩" else "绩点"
        }.attach()

        binding.gradeHost.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

            }
        })
    }

}