package com.example.phoenixnews

import android.app.Application
import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import com.example.phoenixnews.backupworker.NewsWorker
import java.util.concurrent.TimeUnit

class App: Application() {
    companion object {
        lateinit var  appContext : Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }



}