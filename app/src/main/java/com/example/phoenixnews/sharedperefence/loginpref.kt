package com.example.phoenixnews.sharedperefence

import android.content.SharedPreferences
import com.example.phoenixnews.App

class loginpref {
    private lateinit var Pref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val PRIVATE_MODE = 0


  // val username ge

    companion object {
        const val PREF_NAME = "Login_Preference"
        const val IS_LOGIN = "IsLoggedin"
        const val KEY_USERNAME = "username"
        const val KEY_EMAIL = "email"
    }

    init {
        Pref = App.appContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = Pref.edit()

    }

    fun createLoginSession(username: String, email: String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_EMAIL, email)
        editor.apply()
    }

    fun isLoggedin(): Boolean {
        return Pref.getBoolean(IS_LOGIN, false)
    }



    fun getUserDetails(): HashMap<String, String> {

        val user = HashMap<String, String>()
        user[KEY_USERNAME] = Pref.getString(KEY_USERNAME, null) ?: ""
        user[KEY_EMAIL] = Pref.getString(KEY_EMAIL, null) ?: ""
        return user
    }

    fun logoutUser() {
        editor.clear()
        editor.apply()

    }
}

