package com.example.ysuselfstudy.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ysuselfstudy.ui.classschedule.ClassScheduleFragment
import com.example.ysuselfstudy.ui.emptyroom.RoomFragment
import com.example.ysuselfstudy.ui.grade.GPAFragment
import com.example.ysuselfstudy.ui.grade.GradeFragment
import com.example.ysuselfstudy.ui.information.InformationFragment

class FirstVPAdapter(var fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments =
        listOf<Fragment>(RoomFragment(), ClassScheduleFragment(), InformationFragment())

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}

/**
 * 成绩界面的ViewPager2
 */
class GradeVPAdapter(var fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments = listOf<Fragment>(GradeFragment(), GPAFragment())
    override fun getItemCount() = 2
    override fun createFragment(position: Int): Fragment = fragments[position]
}