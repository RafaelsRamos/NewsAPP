package com.example.newsapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class UIUtils {

    companion object {

        fun loadImageWithURL(context: Context, url: String, image: ImageView) {
            Glide.with(context)
                .load(url)
                .into(image)
        }

    }
}