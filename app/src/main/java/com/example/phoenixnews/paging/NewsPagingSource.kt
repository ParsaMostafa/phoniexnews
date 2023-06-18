package com.example.phoenixnews.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.phoenixnews.api.NewsApi
import com.example.phoenixnews.api.RetrofitInstance
import com.example.phoenixnews.model.Article
import com.example.phoenixnews.model.ArticleItemTypes
import java.io.IOException

class NewsPagingSource(
    private val newsApi: NewsApi,
    private val countryCode: String
) : PagingSource<Int, ArticleItemTypes>() {

    private val api = RetrofitInstance.api

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleItemTypes> {
        val position = params.key ?: 1

        return try {
            val response = newsApi.getBreakingNews(countryCode, position)
            val articles = response.body()?.articles ?: emptyList()

            if (response.isSuccessful) {
                val list = mutableListOf<ArticleItemTypes>()
                articles.forEach {
                    list.add(ArticleItemTypes.HeaderModel(it.author))
                    list.add(
                        ArticleItemTypes.ArticleModel(
                            title = it.title,
                            description = it.description,
                            publishedAt = it.publishedAt,
                            urlToImage = it.urlToImage,
                            source = it.source
                        )
                    )
                }
                LoadResult.Page(
                    data = list,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (articles.isEmpty()) null else position + 1
                )
            } else {
                Log.e("1408", "API Error: ${response.message()}")
                // Handle API error if needed
                LoadResult.Error(IOException("API Error: ${response.message()}"))
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleItemTypes>): Int? {
        return null
    }
}
