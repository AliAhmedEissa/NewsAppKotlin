package com.example.seamlabstask.LocalDB.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.seamlabstask.Models.SourcesItem

@Dao
interface SourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllSources(sourcesItemList: List<SourcesItem>)

    @Query("select * from sourcesItem")
    fun getAllSources(): List<SourcesItem>

}