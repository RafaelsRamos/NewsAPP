package com.example.newsapp.fragments.bottom

import android.view.View
import com.example.newsapp.R
import com.example.newsapp.fragments.GenericFragment

class BottomNavigationFragment: GenericFragment() {
    override val TAG = BottomNavigationFragment::class.simpleName.toString()

    override fun getViewInt() = R.layout.bottom_navigation_fragment

    override fun readSavedInstanceState() { }

    override fun saveInstanceState() { }

    override fun createView(view: View) { }

}
