package com.example.newsapp.fragments.main

import android.os.Parcelable
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.example.newsapp.callbacks.OnItemClickedListener
import com.example.newsapp.data.newsapi.INewsLoader
import com.example.newsapp.data.newsapi.NewsAPIService
import com.example.newsapp.data.newsapi.NewsLoader
import com.example.newsapp.data.newsapi.SearchType
import com.example.newsapp.data.newsapi.SearchType.*
import com.example.newsapp.data.newsapi.response.Article
import com.example.newsapp.data.newsapi.response.NewsResponse
import com.example.newsapp.fragments.GenericFragment
import com.example.newsapp.ui.RecyclerSpacingItemDecoration
import com.example.newsapp.ui.adapters.recyclers.ArticlesRecyclerViewAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Card's padding
private const val HorizontalPadding = 30
private const val VerticalPadding = 25

private const val ItemsPerPage = 50
private const val ItemsLeftWhenLoad = 15

abstract class GenericNewsFragment : GenericFragment(), OnItemClickedListener, INewsLoader {
    override val TAG: String = GenericNewsFragment::class.simpleName.toString()

    private lateinit var articleAdapter: ArticlesRecyclerViewAdapter
    private val apiService = NewsAPIService()

    private lateinit var recyclerView: RecyclerView

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

    private val newsLoader = NewsLoader(this)

    //--------------- abstracts

    protected abstract fun getRecycler(): RecyclerView

    //--------------- end of abstracts

    override fun createView(view: View) {
        changeBottomNavigationVisibility(true)
        recyclerView = getRecycler()
        initRecyclerView()
    }

    override fun onRestoreState() {
        recyclerView.layoutManager?.onRestoreInstanceState(recyclerState)
    }

    override fun updateVarsForPause() {
        items = articleAdapter.getItems()
        recyclerState = recyclerView.layoutManager?.onSaveInstanceState()
    }

    private fun loadMore() {
        newsLoader.loadNews()
    }

    protected fun searchByKeyword(@NonNull keyword: String) {
        newsLoader.searchByKeyword(keyword)
        articleAdapter.resetItems()
    }

    protected fun searchGeneric() {
        newsLoader.searchGeneric()
    }

    private fun addDataSet(articles: ArrayList<Article>) {
        articleAdapter.addItems(articles)
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            articleAdapter = ArticlesRecyclerViewAdapter(this@GenericNewsFragment, items)
            addItemDecoration(RecyclerSpacingItemDecoration(VerticalPadding, HorizontalPadding))
            adapter = articleAdapter

            val scrollListener = object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == SCROLL_STATE_IDLE && hasMoreNews && getLastVisibleItemIndex() >= articlesLoaded - ItemsLeftWhenLoad && !loading) {
                        loading = true
                        val recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()
                        recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
                        loadMore()
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

    override fun onNewsLoaded(response: NewsResponse) {
        addDataSet(ArrayList(response.articles))
        loading = false
    }
}
