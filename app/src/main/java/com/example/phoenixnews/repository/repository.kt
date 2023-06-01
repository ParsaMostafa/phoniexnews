package com.example.phoenixnews.repository

import com.example.phoenixnews.db.ArticleDatabase
import com.example.phoenixnews.model.Article

class Repository {
    private val db = ArticleDatabase.getDatabase()



    // Database operations

    fun getAllArticles() = db.getArticleDao().getAllArticles()

    suspend fun upsert(article: Article) {
        db.getArticleDao().upsert(article)
    }

    suspend fun deleteArticle(article: Article) {
        db.getArticleDao().deleteArticle(article)
    }
}
