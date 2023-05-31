package com.example.phoenixnews.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.phoenixnews.App.Companion.appContext
import com.example.phoenixnews.model.Article

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Convertor::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: ArticleDatabase? = null

        fun getDatabase(): ArticleDatabase {
            return INSTANCE ?: synchronized(this) {


                val instance = Room.databaseBuilder(
                    appContext,
                    ArticleDatabase::class.java,
                    "article_db_db"
                )

                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
