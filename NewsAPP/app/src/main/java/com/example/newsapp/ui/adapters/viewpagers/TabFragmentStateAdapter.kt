package com.example.newsapp.ui.adapters.viewpagers

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.fragments.pager.EverythingFragment
import com.example.newsapp.fragments.pager.HotHeadlinesFragmentGeneric

class TabFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    val fragments = arrayListOf(HotHeadlinesFragmentGeneric(), EverythingFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}