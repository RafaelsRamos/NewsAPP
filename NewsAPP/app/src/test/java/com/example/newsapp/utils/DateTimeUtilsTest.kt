package com.example.newsapp.utils

import junit.framework.TestCase
import java.time.Month

class DateTimeUtilsTest : TestCase() {

    fun testFormatDateString() {
        val dateString = "2020-10-15T08:18:19Z"
        val date = DateTimeUtils.formatDateString(dateString)

        assertEquals("15-Oct-2020 08:18:19", date)
    }

    fun testFromStringToLocalDate() {
        val dateString = "2020-10-15T08:18:19Z"
        val localDate = DateTimeUtils.fromStringToLocalDate(dateString)

        assertEquals(2020, localDate.year)
        assertEquals(Month.OCTOBER, localDate.month)
        assertEquals(15, localDate.dayOfMonth)
        assertEquals(8, localDate.hour)
        assertEquals(18, localDate.minute)
        assertEquals(19, localDate.second)
    }
}