package com.example.newsapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.example.newsapp.R
import com.example.newsapp.activities.PrivateAreaActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GenericFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
abstract class GenericFragment : Fragment() {

    protected lateinit var activityReference: PrivateAreaActivity

    abstract val TAG: String

    // TODO: Rename and change types of parameters
    //private var param1: String? = null
    //private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            readSavedInstanceState()
            //param1 = it.getString(ARG_PARAM1)
            //param2 = it.getString(ARG_PARAM2)
        }
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
        createView(requireView())
    }


    /**
     * Get view Layout resource
     */
    @NonNull
    @LayoutRes
    abstract fun getViewInt(): Int

    abstract fun readSavedInstanceState()

    abstract fun createView(view: View)

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