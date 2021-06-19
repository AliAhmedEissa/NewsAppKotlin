package com.example.seamlabstask.LocalDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.seamlabstask.LocalDB.Daos.NewsDao
import com.example.seamlabstask.LocalDB.Daos.SourceDao
import com.example.seamlabstask.Models.ArticlesItem
import com.example.seamlabstask.Models.SourcesItem


@Database(entities = [SourcesItem::class, ArticlesItem::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
public abstract class DBManager() : RoomDatabase() {

    abstract fun sourceDao(): SourceDao

    abstract fun newsDao(): NewsDao

    companion object {
        private var INSTANCE: DBManager? = null

        fun getInstance(): DBManager? {
            if (INSTANCE == null)
                throw  NullPointerException("database is null");
            return INSTANCE
        }

        fun init(context: Context) {
            synchronized(DBManager::class) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    DBManager::class.java, "SeamLabsDB"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
        }
    }


}




