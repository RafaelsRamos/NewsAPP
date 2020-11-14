package com.example.newsapp.fragments.main

import android.os.Bundle
import android.view.View
import com.example.newsapp.R
import com.example.newsapp.data.newsapi.response.Article
import com.example.newsapp.fragments.GenericFragment
import com.example.newsapp.utils.UIUtils
import kotlinx.android.synthetic.main.fragment_detailed_article.*
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private const val ARG_ARTICLE = "param1"

class DetailedArticleFragment: GenericFragment() {
    override val TAG = DetailedArticleFragment::class.simpleName.toString()

    private lateinit var article: Article

    override fun getViewInt() = R.layout.fragment_detailed_article

    override fun readSavedInstanceState() {
        arguments?.let { article = it.getSerializable(ARG_ARTICLE) as Article }
    }

    override fun createView(view: View) {
        changeBottomNavigationVisibility(false)

        val date = LocalDate.parse(article.publishedAt, DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.from(ZoneOffset.UTC)))

        title.text = article.title
        source.text = String.format("%s - %s", article.source.name, date.toString())
        description.text = article.description
        UIUtils.LoadImageWithURL(activityReference, article.urlToImage, image)
    }

    companion object {
        /**
         * @param article   target article
         * @return          instance of DetailedArticleFragment
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(article: Article) =
            DetailedArticleFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ARTICLE, article)
                }
            }
    }
}