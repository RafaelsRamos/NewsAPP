package com.example.newsapp.utils

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class DateTimeUtils {

    companion object {

        @SuppressLint("SimpleDateFormat")
        @JvmStatic
        fun formatDateString(dateString: String) : String {
            val localDate = fromStringToLocalDate(dateString)
            val formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
            return localDate.format(formatter)
        }

        /**
         * Converts String containing a date in "yyyy-MM-ddTHH:mm:ssZ" into datetime
         */
        @JvmStatic
        fun fromStringToLocalDate(dateString: String): LocalDateTime {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss", Locale.ENGLISH)
            val finalDateString = dateString.replace("T","").replace("Z","")
            return LocalDateTime.parse(finalDateString, formatter)
        }

    }
}