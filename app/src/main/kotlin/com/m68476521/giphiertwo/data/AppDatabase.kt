package com.m68476521.giphiertwo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Image::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao

    companion object {
        private var instance: AppDatabase? = null
        private const val DB_NAME = "images.db"

        fun getDatabase(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    if (instance == null) {
                        instance =
                            Room
                                .databaseBuilder(
                                    context.applicationContext,
                                    AppDatabase::class.java,
                                    DB_NAME,
                                ).allowMainThreadQueries()
                                .fallbackToDestructiveMigration()
                                .addCallback(
                                    object : Callback() {
                                        override fun onCreate(db: SupportSQLiteDatabase) {
                                            super.onCreate(db)
                                            GlobalScope.launch(Dispatchers.IO) { rebuildDB(instance) }
                                        }
                                    },
                                ).build()
                    }
                }
            }

            return instance!!
        }
    }
}
