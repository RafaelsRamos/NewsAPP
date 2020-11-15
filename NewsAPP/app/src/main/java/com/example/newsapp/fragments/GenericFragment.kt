package com.example.newsapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.example.newsapp.activities.PrivateAreaActivity
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

abstract class GenericFragment : Fragment(), Serializable {

    abstract val TAG: String

    protected lateinit var activityReference: PrivateAreaActivity

    /**
     * True if the fragment came from backstack, False otherwise
     */
    protected var paused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { readSavedInstanceState() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityReference = activity as PrivateAreaActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(getViewInt(), container, false)
    }

    override fun onStart() {
        super.onStart()
        if (!paused) {
            createView(requireView())
        }
    }

    override fun onResume() {
        super.onResume()
        if (paused) {
            onRestoringState()
        }
    }

    override fun onPause() {
        super.onPause()
        updateVarsForPause()
        paused = true
    }

    /**
     * Get view Layout resource
     */
    @NonNull
    @LayoutRes
    abstract fun getViewInt(): Int

    abstract fun readSavedInstanceState()

    abstract fun saveInstanceState()

    abstract fun createView(view: View)

    /**
     * Method to update UI
     * This method is called onResume. So, UI that should be be updated on return to fragment
     * should be placed here
     */
    protected open fun onRestoringState() { }

    /**
     * Method to update Vars because the fragment is being paused
     */
    protected open fun updateVarsForPause() { }

    protected fun changeBottomNavigationVisibility(show: Boolean) {
        activityReference.changeBottomNavigationVisibility(show)
    }

    /**
     * Retrieve dimension from resources
     */
    protected fun getDimension(@DimenRes res: Int) = resources.getDimension(res)

    /**
     * Retrieve drawable from resources
     */
    protected fun getDrawable(@DrawableRes res: Int) = ContextCompat.getDrawable(activityReference, res)


    companion object {
        //@JvmStatic
        //fun newInstance(param1: String, param2: String) =
        //    GenericFragment().apply {
        //        arguments = Bundle().apply {
        //            putString(ARG_PARAM1, param1)
        //            putString(ARG_PARAM2, param2)
        //        }
        //    }
    }
}