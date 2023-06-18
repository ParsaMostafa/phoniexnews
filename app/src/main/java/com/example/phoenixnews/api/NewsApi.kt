package com.example.phoenixnews.api

import com.example.phoenixnews.model.NewsResponse
import com.example.phoenixnews.utility.Contance.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String,
        @Query("page") pageNumber: Int ,
        @Query("apikey") apiKey: String = API_KEY
    ): Response<NewsResponse>


    @GET("v2/everything")
    suspend fun searchForNews (
        @Query("q")
        searchquery:String
        ,   @Query("page")
        pageNumber:Int
        ,    @Query("apikey")
        apikey:String = API_KEY
    ): Response<NewsResponse>
}