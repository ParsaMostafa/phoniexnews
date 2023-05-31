package com.example.phoenixnews.repository

import com.example.phoenixnews.db.ArticleDatabase
import com.example.phoenixnews.model.Article
import com.example.phoenixnews.sharedperefence.loginpref

class repository () {
    private val db = ArticleDatabase.Companion.getDatabase()


    fun isLoggedIn(): Boolean {
        return loginpref().isLoggedin()
    }

    fun loginUser(username: String, email: String) {
        return loginpref().createLoginSession(username, email)
    }

    fun logoutUser() {
        return  loginpref().logoutUser()
    }
    fun getUserDetails() = loginpref().getUserDetails()

    //database

    fun getAllArticles()= db.getArticleDao().getAllArticles()

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}