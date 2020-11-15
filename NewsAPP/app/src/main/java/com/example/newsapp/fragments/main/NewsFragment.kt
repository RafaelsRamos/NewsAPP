package com.example.newsapp.fragments.main

import android.os.Parcelable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.example.newsapp.R
import com.example.newsapp.callbacks.OnItemClickedListener
import com.example.newsapp.data.newsapi.NewsAPIService
import com.example.newsapp.data.newsapi.SearchType
import com.example.newsapp.data.newsapi.SearchType.*
import com.example.newsapp.data.newsapi.response.Article
import com.example.newsapp.fragments.GenericFragment
import com.example.newsapp.ui.RecyclerSpacingItemDecoration
import com.example.newsapp.ui.adapters.ArticlesRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_main_news.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Card's padding
private const val HorizontalPadding = 30
private const val VerticalPadding = 25

private const val ItemsPerPage = 50
private const val ItemsLeftWhenLoad = 15

class NewsFragment : GenericFragment(), OnItemClickedListener {
    override val TAG: String = NewsFragment::class.simpleName.toString()

    private lateinit var articleAdapter: ArticlesRecyclerViewAdapter
    private val apiService = NewsAPIService()

    //--------- Restore state Vars ---------
    private var items: ArrayList<Article>? = null

    private var recyclerState: Parcelable? = null

    //--------- Generic Vars ---------
    /**
     * True if there is currently news being loaded, False otherwise
     */
    private var loading = true

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

    protected lateinit var country: String
    protected lateinit var keyWord: String
    protected lateinit var category: String

    /**
     * Search type
     */
    private var searchType = GENERIC

    override fun getViewInt() = R.layout.fragment_main_news

    override fun readSavedInstanceState() { }

    override fun saveInstanceState() { }

    override fun createView(view: View) {
        changeBottomNavigationVisibility(true)
        initRecyclerView()
        loadNews()
    }

    override fun onRestoringState() {
        initRecyclerView()
        recyclerView.layoutManager?.onRestoreInstanceState(recyclerState)
    }

    override fun updateVarsForPause() {
        items = articleAdapter.getItems()
        recyclerState = recyclerView.layoutManager?.onSaveInstanceState()
    }

    private fun loadNews() {
        GlobalScope.launch(Dispatchers.Main) {
            val news = when (searchType) {
                GENERIC -> apiService.getNewsAsync(page = this@NewsFragment.page, pageSize = ItemsPerPage).await()
                KEYWORD -> apiService.getNewsByKeywordAsync(keyWord, page = this@NewsFragment.page, pageSize = ItemsPerPage).await()
                COUNTRY -> apiService.getNewsAsync(country, page = this@NewsFragment.page, pageSize = ItemsPerPage).await()
                CATEGORY -> apiService.getNewsByCategoryAsync(category, page = this@NewsFragment.page, pageSize = ItemsPerPage).await()
            }
            addDataSet(ArrayList(news.articles))

            hasMoreNews = news.articles.size == ItemsPerPage
            articlesLoaded += news.articles.size
            page++
            loading = false
        }
    }

    fun changeSearchType(searchType: SearchType) {
        this.searchType = searchType
        page = 0
        articlesLoaded = 0
        hasMoreNews = true
    }

    private fun addDataSet(articles: ArrayList<Article>) {
        articleAdapter.addItems(articles)
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            articleAdapter = ArticlesRecyclerViewAdapter(this@NewsFragment, items)
            addItemDecoration(RecyclerSpacingItemDecoration(VerticalPadding, HorizontalPadding))
            adapter = articleAdapter

            val scrollListener = object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == SCROLL_STATE_IDLE && hasMoreNews && getLastVisibleItemIndex() >= articlesLoaded - ItemsLeftWhenLoad && !loading) {
                        loading = true
                        val recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()
                        recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
                        loadNews()
                    }
                }
            }

            addOnScrollListener(scrollListener)
        }
    }

    private fun getLastVisibleItemIndex(): Int {
        return (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
    }

    override fun onItemClicked(position: Int) {
        activityReference.loadFragment(DetailedArticleFragment.newInstance(articleAdapter.items[position]))
    }
}
