package com.example.newsapp.data.newsapi

enum class SearchType {
    /**
     * Search without a specific search type
     */
    GENERIC,

    /**
     * Search by keyboard
     */
    KEYWORD,

    /**
     * Search in a specific country
     */
    COUNTRY,

    /**
     * Search by category
     */
    CATEGORY
}