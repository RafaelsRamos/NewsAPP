package com.example.newsapp.fragments.pager

import android.view.View
import com.example.newsapp.R
import com.example.newsapp.fragments.GenericFragment

class EverythingFragment: GenericFragment() {
    override val TAG = EverythingFragment::class.toString()

    override fun getViewInt() = R.layout.fragment_tab_everything

    override fun readSavedInstanceState() { }

    override fun saveInstanceState() { }

    override fun createView(view: View) { }

}