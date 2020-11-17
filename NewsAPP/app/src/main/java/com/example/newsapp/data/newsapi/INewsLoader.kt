package com.example.newsapp.data.newsapi

import com.example.newsapp.data.newsapi.response.NewsResponse

interface INewsLoader {

    /**
     * On news loaded, pass NewsResponse
     */
    fun onNewsLoaded(response: NewsResponse)

}