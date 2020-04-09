package com.example.ysuselfstudy.ui.first

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

import com.example.ysuselfstudy.R
import com.example.ysuselfstudy.adapter.ViewPagerAdapter
import com.example.ysuselfstudy.ui.classschedule.ClassScheduleFragment
import com.example.ysuselfstudy.ui.emptyroom.RoomFragment
import com.example.ysuselfstudy.ui.userinfo.UserInfoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment() {
    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: FragmentPagerAdapter
    lateinit var bottomNav: BottomNavigationView

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var view: View = inflater.inflate(R.layout.main_fragment, container, false)

        val fragments =
            listOf<Fragment>(RoomFragment(), ClassScheduleFragment(), UserInfoFragment())

        viewPager = view.findViewById(R.id.viewpager)
        bottomNav = view.findViewById(R.id.bottom_nav)
        viewPagerAdapter = ViewPagerAdapter(fragments, childFragmentManager)
        viewPager.adapter = viewPagerAdapter

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

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                bottomNav.menu.getItem(position).setChecked(true)
            }
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }


}
