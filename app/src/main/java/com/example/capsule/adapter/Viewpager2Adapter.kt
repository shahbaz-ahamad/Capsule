package com.example.capsule.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class Viewpager2Adapter(
    private val fragmentActivity: FragmentActivity,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}