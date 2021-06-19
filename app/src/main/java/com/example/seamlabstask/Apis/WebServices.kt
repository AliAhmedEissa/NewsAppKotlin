package com.example.seamlabstask.Apis

import com.example.seamlabstask.Models.NewsResponse
import com.example.seamlabstask.Models.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WebServices {
    @GET("sources")
    fun getNewsSource(@Query("apiKey") apiKey: String?): Call<SourcesResponse?>?

    @GET("everything")
    fun getNewsBySourceId(
        @Query("apiKey") apiKey: String?,
        @Query("sources") sources: String?
    ): Call<NewsResponse>
}