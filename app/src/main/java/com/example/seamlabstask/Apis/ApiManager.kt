package com.example.seamlabstask.Apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    private var retrofit: Retrofit? = null
    private val baseUrl = "https://newsapi.org/v2/"

    private val instance: Retrofit?
        private get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

    val apis: WebServices
        get() = instance!!.create(WebServices::class.java)
}
