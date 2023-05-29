package com.example.phoenixnews.fragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.phoenixnews.api.RetrofitInstance
import com.example.phoenixnews.model.Article
import com.example.phoenixnews.paging.NewsPagingSource
import com.example.phoenixnews.utility.Contance.Companion.PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class NewsViewModel : ViewModel() {
    private val newsApi = RetrofitInstance.api


        fun getBreakingNewsFlow(countryCode: String): Flow<PagingData<Article>> {
            val pagingConfig = PagingConfig(PAGE_SIZE)

            return Pager(
                config = pagingConfig,
                pagingSourceFactory = { NewsPagingSource(newsApi, countryCode) }
            ).flow
        }
    }