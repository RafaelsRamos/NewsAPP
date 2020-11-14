package com.example.newsapp.data.newsapi

import android.util.Log
import com.example.newsapp.data.newsapi.response.NewsResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "8be09e9b76894de6a3079af95eadf606"

//http://newsapi.org/v2/top-headlines?country=us&apiKey=8be09e9b76894de6a3079af95eadf606
//http://newsapi.org/v2/top-headlines?category=science&apiKey=8be09e9b76894de6a3079af95eadf606
//http://newsapi.org/v2/top-headlines?q=AntonioCosta&apiKey=8be09e9b76894de6a3079af95eadf606

interface NewsAPIService {

    @GET("top-headlines")
    fun getNewsAsync(
        @Query("country") targetLocation: String = "pt",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 100
    ): Deferred<NewsResponse>

    @GET("top-headlines")
    fun getNewsByKeywordAsync(
        @Query("q") keyword: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 100
    ): Deferred<NewsResponse>

    @GET("top-headlines")
    fun getNewsByCategoryAsync(
        @Query("q") keyword: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 100
    ): Deferred<NewsResponse>


    companion object {

        operator fun invoke(): NewsAPIService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("apiKey", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                Log.d("URL", request.url().toString())
                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://newsapi.org/v2/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsAPIService::class.java)
        }
    }
}