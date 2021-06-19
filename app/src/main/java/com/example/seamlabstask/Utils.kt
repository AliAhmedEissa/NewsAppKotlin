package com.example.seamlabstask

import com.example.seamlabstask.Apis.ApiManager
import java.text.SimpleDateFormat

class Utils { // Class For Common Data Usage
    companion object {
        val apiManager = ApiManager().apis
        fun formatDate(date: String): String {
            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val output = SimpleDateFormat("dd/MM/yyyy")
            return output.format(input.parse(date))
        }
    }
}