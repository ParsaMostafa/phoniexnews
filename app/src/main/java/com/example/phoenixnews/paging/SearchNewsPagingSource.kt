package com.example.phoenixnews.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.phoenixnews.api.NewsApi
import com.example.phoenixnews.model.Article

class SearchNewsPagingSource(private val api: NewsApi, private val searchQuery: String) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            val pageNumber = params.key ?: 1

            val response = api.searchForNews(searchQuery, pageNumber)

            return if (response.isSuccessful) {
                val articles = response.body()?.articles ?: emptyList()
                val prevKey = if (pageNumber == 1) null else pageNumber - 1
                val nextKey = if (articles.isEmpty()) null else pageNumber + 1

                LoadResult.Page(
                    data = articles,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(Exception("Failed to fetch data from the NewsApi"))
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }
}
