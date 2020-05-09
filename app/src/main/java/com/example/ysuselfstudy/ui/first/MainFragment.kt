package com.example.ysuselfstudy.ui.first


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.ysuselfstudy.MainViewModel

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.adapter.ViewPagerAdapter
import com.example.ysuselfstudy.databinding.AppBarMainBinding
import com.example.ysuselfstudy.logic.getWeek
import com.example.ysuselfstudy.ui.classschedule.ClassScheduleViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment() {
    lateinit var viewPager: ViewPager2
    lateinit var viewPagerAdapter: FragmentStateAdapter
    lateinit var bottomNav: BottomNavigationView
    lateinit var mainViewModel: MainViewModel
    lateinit var navController: NavController
    lateinit var courseViewModel: ClassScheduleViewModel
    lateinit var toolbar: Toolbar
    private val TAG = "MainFragment"
    lateinit var barBinding: AppBarMainBinding

    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.main_fragment, container, false)

        viewPager = view.findViewById(R.id.viewpager)
        bottomNav = view.findViewById(R.id.bottom_nav)
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 1
        navController = findNavController()

        setHasOptionsMenu(true)


        toolbar = requireActivity().findViewById(R.id.toolbar)


        bottomNav.setOnNavigationItemSelectedListener() {
            var id = it.itemId
            viewPager.setCurrentItem(
                when (id) {
                    R.id.empty_room -> 0
                    R.id.class_schedule -> 1
                    R.id.user_info -> 2
                    else -> 0
                }
            )
            return@setOnNavigationItemSelectedListener false
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                requireActivity().invalidateOptionsMenu()
            }

            override fun onPageSelected(position: Int) {
                bottomNav.menu.getItem(position).setChecked(true)
                when (position) {
                    0 -> {
                        toolbar.visibility = View.VISIBLE
                        toolbar.title = "空教室"
                    }
                    1 -> {
                        toolbar.title = "第${getWeek()}周"
                    }
                    2 -> {
                        toolbar.visibility = View.VISIBLE
                        toolbar.title = "公告"
                    }
                }
            }
        })

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        courseViewModel =
            ViewModelProvider(requireActivity()).get(ClassScheduleViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.course_menu, menu)
        if (viewPager.currentItem == 1) {
            menu.findItem(R.id.refresh_course).setVisible(true)
        } else {
            menu.findItem(R.id.refresh_course).setVisible(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> return super.onOptionsItemSelected(item)
            R.id.refresh_course -> courseViewModel.clearCourse(mainViewModel.authenticationState == MainViewModel.AuthenticationState.AUTHENTICATED)
        }
        return true

    }
}
