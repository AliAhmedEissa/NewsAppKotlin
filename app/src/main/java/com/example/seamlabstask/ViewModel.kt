package com.example.seamlabstask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.seamlabstask.Models.ArticlesItem
import com.example.seamlabstask.Models.SourcesItem
import com.example.seamlabstask.Repository.NewsRepo
import com.example.seamlabstask.Repository.SourcesRepo


class ViewModel(application: Application) : AndroidViewModel(application) {

    var context = application.applicationContext

    var sourcesRepo = SourcesRepo()
    var newsRepo = NewsRepo()

    var sourceList: MutableLiveData<List<SourcesItem>>
    var newsList: MutableLiveData<List<ArticlesItem>>


    init {
        sourceList = sourcesRepo.cacheSourcesList
        newsList = newsRepo.articlesItem
    }

    companion object {
        var articlesItem: ArticlesItem? = null
    }


    fun getNewsSources() {
        sourcesRepo.getSources()
    }

    fun getNewsSourcesbyID(sourceId: String) {
        newsRepo.getNewsSources(sourceId)
    }

    fun getFromCache() {
        newsRepo.getNewsFromCache()
        sourcesRepo.getSourcesFromCache()
    }
}