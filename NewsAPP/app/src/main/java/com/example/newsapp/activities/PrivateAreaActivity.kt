package com.example.newsapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import com.example.newsapp.R
import com.example.newsapp.fragments.GenericFragment
import com.example.newsapp.fragments.bottom.BottomNavigationFragment
import com.example.newsapp.fragments.header.HeaderSearchFragment
import com.example.newsapp.fragments.main.NewsFragment
import com.example.newsapp.managers.ApplicationManager
import kotlinx.android.synthetic.main.activity_private_area.*

private const val BACK_STACK_ROOT_TAG = "root_fragment"

class PrivateAreaActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_area)
        setConfigurations()

        //redirect("http://www.google.com")
        setHeader()
        setBottomNavigation()

        changeBottomNavigationVisibility(true)
        loadFragment(NewsFragment())
    }



    private fun setHeader() {
        loadWithoutAddingToBackStack(HeaderSearchFragment(), R.id.headerFrameLayout)
    }

    private fun setBottomNavigation() {
        loadWithoutAddingToBackStack(BottomNavigationFragment(), R.id.bottomNavigationFrameLayout)
    }

    private fun setConfigurations() {
        ApplicationManager.setDefaults(this)
    }

    fun changeBottomNavigationVisibility(show: Boolean) {
        bottomNavigationFrameLayout.visibility = if (show) View.VISIBLE else View.GONE
    }

    /**
     * Load given GenericFragment into activity_private_area.xml's R.id.mainFrameLayout
     * and if desired, adds it to the backstack
     * @param fragment          Generic Fragment
     */
    fun loadFragment(fragment: GenericFragment) {
        loadFragment(fragment, R.id.mainFrameLayout)
    }

    /**
     * Load given GenericFragment into the passed container id
     * @param fragment      Given fragment
     * @param containerId   Target container ID
     */
    private fun loadFragment(fragment: GenericFragment, @IdRes containerId: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(BACK_STACK_ROOT_TAG)
            .commit()
    }

    private fun loadWithoutAddingToBackStack(fragment: GenericFragment, @IdRes containerId: Int) {
        supportFragmentManager
            .beginTransaction()
            .add(containerId, fragment, fragment.TAG)
            .commit()
    }

    /**
     * Redirect to given url
     */
    fun redirect(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}