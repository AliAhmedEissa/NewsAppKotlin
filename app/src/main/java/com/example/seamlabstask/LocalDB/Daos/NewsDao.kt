package com.example.seamlabstask.LocalDB.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.seamlabstask.Models.ArticlesItem


@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllArticles(articlesItemList: List<ArticlesItem>)

    @Query("select * from articlesitem ")
    fun getAllArticles(): List<ArticlesItem>


    @Query("select * from articlesitem where sourceId = :sou")
    fun getArticles(sou: String): List<ArticlesItem>
}