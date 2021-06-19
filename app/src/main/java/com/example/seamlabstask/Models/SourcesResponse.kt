package com.example.seamlabstask.Models

import androidx.room.Entity

@Entity
data class SourcesResponse(
    val status: String,
    val articles: String,
    val sources: List<SourcesItem>,
    val message: String,
)