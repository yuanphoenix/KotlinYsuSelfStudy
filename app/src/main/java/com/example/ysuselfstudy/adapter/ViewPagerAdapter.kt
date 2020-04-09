package com.example.ysuselfstudy.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(var fragments: List<Fragment>,var fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size
}