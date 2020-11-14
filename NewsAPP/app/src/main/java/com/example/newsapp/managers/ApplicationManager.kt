package com.example.newsapp.managers

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.util.Log
import com.example.newsapp.activities.PrivateAreaActivity
import com.example.newsapp.configurations.GenericPreferencesKeys
import java.util.*

class ApplicationManager() {

    companion object {

        private const val TAG = "ApplicationManager"

        /**
         * Shared preferences file
         */
        private val sharedPrefFile = "kotlinsharedpreference"

        /**
         * Reference to the Private Area activity
         */
        lateinit var activity: PrivateAreaActivity

        /**
         * Shared preferences
         */
        lateinit var sharedPreferences: SharedPreferences


        fun setDefaults(activity: PrivateAreaActivity) {
            this.activity = activity
            sharedPreferences = activity.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        }

        fun setLocaleLanguage(isoCode: String?) {
            if (isoCode == null) return

            Log.d(TAG, "Changed language to " + isoCode)

            val resources = activity.resources
            val locale = Locale(isoCode)
            val config: Configuration = resources.configuration

            Locale.setDefault(locale)
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)

            val storedLanguage = sharedPreferences.getString(GenericPreferencesKeys.Languages.key, Locale.getDefault().displayLanguage)
            if (!storedLanguage.equals(isoCode)) {
                changeLanguagePreference(isoCode)
            }
        }

        fun changeLanguagePreference(iso: String) {
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString(GenericPreferencesKeys.Languages.key, iso)
            editor.apply()
            editor.commit()
        }
    }

}