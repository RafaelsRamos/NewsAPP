package com.example.newsapp.data.newsapi.response


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Source(
    @SerializedName("id")
    @Serializable
    val id: String,
    @SerializedName("name")
    val name: String
): java.io.Serializable