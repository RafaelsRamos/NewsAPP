package com.example.newsapp.fragments.header

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.R
import com.example.newsapp.fragments.GenericFragment
import kotlinx.android.synthetic.main.header_search_fragment.*


private const val AnimationDuration = 500L

class HeaderSearchFragment: GenericFragment() {
    override val TAG = HeaderSearchFragment::class.toString()

    var isEditTextOpen = false
    var isAnimationOngoing = false

    override fun getViewInt() = R.layout.header_search_fragment

    override fun readSavedInstanceState() { }

    override fun saveInstanceState() { }

    override fun createView(view: View) {
        search_iv.setOnClickListener {
            isAnimationOngoing = true
            changeEditTextState()
        }
    }

    private fun changeEditTextState() {
        val finalWidth = if (isEditTextOpen) getDimension(R.dimen.search_edit_box_min_width).toInt() else getDimension(R.dimen.search_edit_box_max_width).toInt()
        val anim = ValueAnimator.ofInt(search_et.measuredWidth, finalWidth)

        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = search_et.layoutParams
            layoutParams.width = value
            search_et.layoutParams = layoutParams
        }

        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                isEditTextOpen = !isEditTextOpen
                isAnimationOngoing = false
            }
        })

        anim.duration = AnimationDuration
        anim.start()
    }
}