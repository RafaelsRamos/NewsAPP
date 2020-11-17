package com.example.newsapp.data.newsapi

import androidx.annotation.NonNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val ItemsPerPage = 50

class NewsLoader(private val callback: INewsLoader) {

    /**
     * Reference to NewsAPIService
     */
    private val apiService = NewsAPIService()

    /**
     * True if there are more news to be viewed, False otherwise
     */
    private var hasMoreNews = true

    /**
     * Number of articles loaded
     */
    private var articlesLoaded = 0

    /**
     * Current page being viewed
     */
    private var page = 1

    private lateinit var country: String
    private lateinit var keyword: String
    private lateinit var category: String

    /**
     * Search type
     */
    private var searchType = SearchType.GENERIC

    fun loadNews() {
        if (!hasMoreNews) return
        GlobalScope.launch(Dispatchers.Main) {
            val news = when (searchType) {
                SearchType.GENERIC -> apiService.getNewsAsync(page = this@NewsLoader.page, pageSize = ItemsPerPage).await()
                SearchType.KEYWORD -> apiService.getNewsByKeywordAsync(keyword, page = this@NewsLoader.page, pageSize = ItemsPerPage).await()
                SearchType.COUNTRY -> apiService.getNewsAsync(country, page = this@NewsLoader.page, pageSize = ItemsPerPage).await()
                SearchType.CATEGORY -> apiService.getNewsByCategoryAsync(category, page = this@NewsLoader.page, pageSize = ItemsPerPage).await()
            }

            hasMoreNews = news.articles.size == ItemsPerPage
            articlesLoaded += news.articles.size
            page++
            callback.onNewsLoaded(news)
        }
    }

    fun searchGeneric() {
        resetSearch(SearchType.GENERIC)
    }

    fun searchByKeyword(@NonNull keyword: String) {
        this.keyword = keyword
        resetSearch(SearchType.KEYWORD)
    }

    private fun resetSearch(searchType: SearchType) {
        this.searchType = searchType
        page = 0
        articlesLoaded = 0
        hasMoreNews = true
        loadNews()
    }
}