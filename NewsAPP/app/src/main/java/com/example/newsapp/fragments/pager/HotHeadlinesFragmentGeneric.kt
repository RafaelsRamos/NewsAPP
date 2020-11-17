package com.example.newsapp.fragments.pager

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.fragments.main.GenericNewsFragment
import kotlinx.android.synthetic.main.fragment_main_news.*

class HotHeadlinesFragmentGeneric : GenericNewsFragment() {
    override val TAG = HotHeadlinesFragmentGeneric::class.toString()

    override fun getRecycler(): RecyclerView = recyclerView

    override fun getViewInt() = R.layout.fragment_tab_hot_headlines

    override fun readSavedInstanceState() { }

    override fun saveInstanceState() { }

    override fun createView(view: View) {
        super.createView(view)
        searchGeneric()
    }

}