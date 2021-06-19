package com.example.seamlabstask.LocalDB

import androidx.room.TypeConverter
import com.example.seamlabstask.Models.SourcesItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converter {
    @TypeConverter
    fun fromString(value: String?): SourcesItem {
        val listType: Type = object : TypeToken<SourcesItem>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayLisr(list: SourcesItem): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}