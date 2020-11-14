package com.example.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.callbacks.OnItemClickedListener
import com.example.newsapp.data.newsapi.response.Article
import kotlinx.android.synthetic.main.item_cardview.view.*

class ArticlesRecyclerViewAdapter(private val listener: OnItemClickedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cardview, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ArticleViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount() = items.size

    fun submitList(articles: ArrayList<Article>) {
        items = articles
        notifyDataSetChanged()
    }

    fun addItems(articles: ArrayList<Article>) {
        items.addAll(articles)
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val articleTitle = itemView.title
        private val articleSource = itemView.source
        private val articleImage = itemView.image

        init {
            itemView.setOnClickListener { listener.onItemClicked(adapterPosition) }
        }

        fun bind(article: Article) {
            articleTitle.text = article.title
            articleSource.text = article.source.name

            Glide.with(itemView.context)
                .load(article.urlToImage)
                .into(articleImage)
        }
    }
}