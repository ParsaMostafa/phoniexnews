package com.example.phoenixnews.fragments.viewmodel

import androidx.lifecycle.ViewModel
import com.example.phoenixnews.sharedperefence.loginpref

class ViewmodelPerf : ViewModel() {

    private val loginPreference = loginpref()



    fun isLoggedIn(): Boolean {
        return loginPreference.isLoggedIn
    }

    fun getUsername(): String {
        return loginPreference.username
    }

    fun getEmail(): String {
        return loginPreference.email
    }

    fun createLoginSession(username: String, email: String) {
        loginPreference.createLoginSession(username, email)
    }

    fun checkLogin() {
        loginPreference.checkLogin()
    }

    fun logoutUser() {
        loginPreference.logoutUser()
    }
}