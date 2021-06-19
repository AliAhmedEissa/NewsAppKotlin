package com.example.seamlabstask

import android.app.Application
import com.example.seamlabstask.LocalDB.DBManager


class Application : Application() {


    override fun onCreate() {
        super.onCreate()
        // Initialize Database
        DBManager.init(this)
    }

}