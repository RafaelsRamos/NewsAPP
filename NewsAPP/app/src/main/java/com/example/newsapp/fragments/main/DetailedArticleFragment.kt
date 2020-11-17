package com.example.newsapp.fragments.main

import android.os.Bundle
import android.view.View
import com.example.newsapp.R
import com.example.newsapp.data.newsapi.response.Article
import com.example.newsapp.fragments.GenericFragment
import com.example.newsapp.utils.DateTimeUtils
import com.example.newsapp.utils.UIUtils
import kotlinx.android.synthetic.main.fragment_detailed_article.*


private const val ARG_ARTICLE = "article_arg"

class DetailedArticleFragment: GenericFragment() {
    override val TAG = DetailedArticleFragment::class.simpleName.toString()

    @Transient private lateinit var article: Article

    override fun getViewInt() = R.layout.fragment_detailed_article

    override fun readSavedInstanceState() {
        arguments?.let { article = it.getSerializable(ARG_ARTICLE) as Article }
    }

    override fun saveInstanceState() { }

    override fun createView(view: View) {
        changeBottomNavigationVisibility(false)

        title.text = article.title
        source.text = String.format(
            "%s - %s", article.source.name, DateTimeUtils.formatDateString(
                article.publishedAt
            )
        )
        description.text = article.description
        articleUrl.text = article.url
        UIUtils.loadImageWithURL(activityReference, article.urlToImage, image)

        articleUrl.setOnClickListener { activityReference.redirect(article.url) }
    }

    override fun onRestoreState() { }

    companion object {
        /**
         * @param article   target article
         * @return          instance of DetailedArticleFragment
         */
        @JvmStatic
        fun newInstance(article: Article) =
            DetailedArticleFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ARTICLE, article)
                }
            }
    }
}