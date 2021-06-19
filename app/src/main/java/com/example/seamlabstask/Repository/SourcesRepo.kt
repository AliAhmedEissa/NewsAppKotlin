package com.example.seamlabstask.Repository

import androidx.lifecycle.MutableLiveData
import com.example.seamlabstask.BuildConfig
import com.example.seamlabstask.LocalDB.DBManager
import com.example.seamlabstask.Models.SourcesItem
import com.example.seamlabstask.Models.SourcesResponse
import com.example.seamlabstask.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SourcesRepo {
    val cacheSourcesList = MutableLiveData<List<SourcesItem>>()


    fun getSources() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = Utils.apiManager.getNewsSource(BuildConfig.Apikey)
            call!!.enqueue(object : Callback<SourcesResponse?> {
                override fun onResponse(
                    call: Call<SourcesResponse?>,
                    response: Response<SourcesResponse?>
                ) {
                    if (response.isSuccessful)
                        cacheSources(response.body()!!.sources)
                }

                override fun onFailure(call: Call<SourcesResponse?>, t: Throwable) {
                    getSourcesFromCache()
                }


            })

        }
    }

    private fun cacheSources(sources: List<SourcesItem>) {
        DBManager.getInstance()!!.sourceDao().addAllSources(sources)
        getSourcesFromCache()
    }


    fun getSourcesFromCache() {
        val sourcesItems: List<SourcesItem> = DBManager.getInstance()!!.sourceDao().getAllSources()
        cacheSourcesList.postValue(sourcesItems)

    }


}