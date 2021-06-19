package com.example.seamlabstask.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SourcesItem(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val description: String,
    val language: String,
    val country: String,
    val category: String,
    val url: String,
)