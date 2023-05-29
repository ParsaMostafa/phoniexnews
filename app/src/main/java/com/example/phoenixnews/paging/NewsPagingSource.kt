package com.example.phoenixnews.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.phoenixnews.api.NewsApi
import com.example.phoenixnews.model.Article
import java.io.IOException

class NewsPagingSource(
    private val newsApi: NewsApi,
    private val countryCode: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        val position = params.key ?: 1

        return try {
            val response = newsApi.getBreakingNews(countryCode, position)
            val articles = response.body()?.articles ?: emptyList()

            LoadResult.Page(
                data = articles,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (articles.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }
}