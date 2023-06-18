package com.example.phoenixnews.fragments.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.phoenixnews.api.RetrofitInstance
import com.example.phoenixnews.model.Article
import com.example.phoenixnews.model.ArticleItemTypes
import com.example.phoenixnews.paging.NewsPagingSource
import com.example.phoenixnews.paging.SearchNewsPagingSource
import com.example.phoenixnews.repository.Repository
import com.example.phoenixnews.utility.Contance.Companion.API_KEY

import com.example.phoenixnews.utility.Contance.Companion.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val newsApi = RetrofitInstance.api
    private val repository = Repository()
    var article: Article? = null


    // api call and create a list of articles pass it to fragment for adaptor usage

    /*
    val listflow  = MutableStateFlow<List<ArticleItemTypes>>(emptyList())


    fun getBreakingNewsFlow(countryCode: String) {
        viewModelScope.launch {
            val response = newsApi.getBreakingNews(countryCode, 1, API_KEY)

            if (response.isSuccessful) {
                val articles = response.body()?.articles

                val list = mutableListOf<ArticleItemTypes>()
                articles?.forEach {
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
                listflow.emit(list)
            } else {
                Log.e("1408", "API Error: ${response.message()}")
                // Handle API error if needed
            }
        }
    }
    */



        fun getBreakingNewsFlow(countryCode: String): Flow<PagingData<ArticleItemTypes>> {
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