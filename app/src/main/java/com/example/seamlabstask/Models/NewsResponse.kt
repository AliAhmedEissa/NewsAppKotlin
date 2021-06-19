package com.example.seamlabstask.Models

import androidx.room.Entity

@Entity
data class NewsResponse(
    val articles: List<ArticlesItem>,
    val totalResults: Int,
    val status: String,
    val message: String,
    val title: String,
)