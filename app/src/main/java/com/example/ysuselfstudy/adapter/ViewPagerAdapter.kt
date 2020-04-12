package com.example.ysuselfstudy.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ysuselfstudy.ui.classschedule.ClassScheduleFragment
import com.example.ysuselfstudy.ui.emptyroom.RoomFragment
import com.example.ysuselfstudy.ui.userinfo.UserInfoFragment

class ViewPagerAdapter(var fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments =
        listOf<Fragment>(RoomFragment(), ClassScheduleFragment(), UserInfoFragment())

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment =
        fragments.get(position)
}