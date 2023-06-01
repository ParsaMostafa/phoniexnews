package com.example.phoenixnews.sharedperefence

import android.content.Intent
import android.content.SharedPreferences
import com.example.phoenixnews.App
import com.example.phoenixnews.LoginActivity

class loginpref {
    private lateinit var pref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val PRIVATE_MODE = 0

    companion object {
        const val PREF_NAME = "Login_Preference"
        const val IS_LOGIN = "IsLoggedIn"
        const val KEY_USERNAME = "username"
        const val KEY_EMAIL = "email"
    }

    init {
        pref = App.appContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    var isLoggedIn: Boolean
        get() = pref.getBoolean(IS_LOGIN, false)
        set(value) {
            editor.putBoolean(IS_LOGIN, value)
            editor.apply()
        }

    var username: String
        get() = pref.getString(KEY_USERNAME, "") ?: ""
        set(value) {
            editor.putString(KEY_USERNAME, value)
            editor.apply()
        }

    var email: String
        get() = pref.getString(KEY_EMAIL, "") ?: ""
        set(value) {
            editor.putString(KEY_EMAIL, value)
            editor.apply()
        }

    fun createLoginSession(username: String, email: String) {
        isLoggedIn = true
        this.username = username
        this.email = email
    }

    fun checkLogin() {
        if (!isLoggedIn) {
            val i = Intent(App.appContext, LoginActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            App.appContext.startActivity(i)
        }
    }

    fun getUserDetails(): HashMap<String, String> {
        val user = HashMap<String, String>()
        user[KEY_USERNAME] = username
        user[KEY_EMAIL] = email
        return user
    }

    fun logoutUser() {
        isLoggedIn = false
        editor.clear()
        editor.apply()
    }
}
