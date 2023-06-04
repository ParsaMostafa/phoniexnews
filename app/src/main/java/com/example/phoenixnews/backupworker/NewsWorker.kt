package com.example.phoenixnews.backupworker
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.phoenixnews.R
import com.example.phoenixnews.api.RetrofitInstance
import com.example.phoenixnews.model.Article
import com.example.phoenixnews.paging.NewsPagingSource
import com.example.phoenixnews.utility.Contance
import kotlinx.coroutines.flow.Flow

class NewsWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {
    private val channelId = "news_channel"
    private val countryCode = "us"
    private val newsApi = RetrofitInstance.api


    override suspend fun doWork(): Result {


        fetchnewBreakingnews()

        // Create a notification
        val notification = createNotification()

        // Display the notification
        showNotification(notification)

        // Indicate success or failure
        return Result.success()
    }

    suspend fun fetchnewBreakingnews(): Flow<PagingData<Article>> {
        val pagingConfig = PagingConfig(Contance.PAGE_SIZE)

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { NewsPagingSource(newsApi, countryCode) }
        ).flow
    }


    private fun createNotification(): Notification {
        // Create a notification channel (required for Android 8.0 and above)
        createNotificationChannel()

        // Build the notification
        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.baseline_newspaper_24)
            .setContentTitle("News Notification")
            .setContentText("New news available!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        return builder.build()
    }

    private fun createNotificationChannel() {
        // Check if the SDK version is 26 or above (required for creating notification channels)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "News Channel"
            val channelDescription = "Channel for news notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = channelDescription

            // Register the channel with the system
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(notification: Notification) {
        val notificationId = 1

        // Get the notification manager
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Show the notification
        notificationManager.notify(notificationId, notification)
    }
}
