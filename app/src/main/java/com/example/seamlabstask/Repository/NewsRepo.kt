package com.example.seamlabstask.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.seamlabstask.BuildConfig
import com.example.seamlabstask.LocalDB.DBManager
import com.example.seamlabstask.Models.ArticlesItem
import com.example.seamlabstask.Models.NewsResponse
import com.example.seamlabstask.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsRepo {

    val articlesItem = MutableLiveData<List<ArticlesItem>>()


    fun getNewsSources(sourceId: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val call = Utils.apiManager.getNewsBySourceId(BuildConfig.Apikey, sourceId)
            call.enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful) {
                        cacheNews(response.body()!!.articles)
                        articlesItem.postValue(response.body()!!.articles)

                    }

                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    getNewsFromCache()
                }

            })

        }


    }

    fun cacheNews(articles: List<ArticlesItem>) {
        DBManager.getInstance()!!.newsDao().addAllArticles(articles)
    }

    fun getNewsFromCache() {
        var articlesItemList: List<ArticlesItem> =
            DBManager.getInstance()!!.newsDao().getAllArticles()
        articlesItem.postValue(articlesItemList)
    }

}