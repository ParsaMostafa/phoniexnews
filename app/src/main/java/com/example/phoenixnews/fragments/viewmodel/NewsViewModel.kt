package com.example.phoenixnews.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.phoenixnews.api.RetrofitInstance
import com.example.phoenixnews.model.Article
import com.example.phoenixnews.paging.NewsPagingSource
import com.example.phoenixnews.paging.SearchNewsPagingSource
import com.example.phoenixnews.repository.repository
import com.example.phoenixnews.utility.Contance.Companion.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val newsApi = RetrofitInstance.api
    private val repository = repository()
    var article: Article? = null


        fun getBreakingNewsFlow(countryCode: String): Flow<PagingData<Article>> {
            val pagingConfig = PagingConfig(PAGE_SIZE)

            return Pager(
                config = pagingConfig,
                pagingSourceFactory = { NewsPagingSource(newsApi, countryCode) }
            ).flow
        }

    fun searchForNews(searchQuery: String): Flow<PagingData<Article>> {
        val pagingConfig = PagingConfig(PAGE_SIZE)
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { SearchNewsPagingSource(newsApi, searchQuery) }
        ).flow
    }
//database
fun deleteArticle(article: Article) = viewModelScope.launch {
    repository.deleteArticle(article)
}

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.upsert(article)
    }

    val savedArticles: Flow<List<Article>> = repository.getAllArticles()




    }