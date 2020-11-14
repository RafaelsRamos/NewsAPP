package com.example.newsapp.fragments.complete

import android.content.res.Configuration
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.newsapp.configurations.Languages
import com.example.newsapp.fragments.GenericFragment
import com.example.newsapp.managers.ApplicationManager
import com.example.newsapp.R
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*

class SettingsFragment : GenericFragment() {

    private val languages = arrayOf(Languages.ENGLISH.fullName, Languages.PORTUGUESE.fullName)

    //---------------------- Generic Fragment ----------------------
    override val TAG = SettingsFragment::class.simpleName.toString()

    override fun getViewInt() = R.layout.fragment_settings

    override fun readSavedInstanceState() { }

    override fun createView(view: View) {

        if (languageSpinner != null) {
            val adapter = ArrayAdapter(activityReference, R.layout.spinner_item, languages)
            languageSpinner.adapter = adapter
        }

        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Languages.values().forEach { language -> if (language.fullName == languages[position]) ApplicationManager.setLocaleLanguage(language.iso) }
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) { }
        }
    }
}