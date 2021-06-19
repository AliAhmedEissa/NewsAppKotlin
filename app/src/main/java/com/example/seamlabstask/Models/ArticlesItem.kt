package com.example.seamlabstask.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ArticlesItem {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var sourceId: String = ""
    var title: String = ""
    var source: SourcesItem? = null
    var urlToImage: String = ""
    var description: String = ""
    var publishedAt: String = ""
    var author: String? = ""
    var url: String = ""
    var content: String = ""
}

