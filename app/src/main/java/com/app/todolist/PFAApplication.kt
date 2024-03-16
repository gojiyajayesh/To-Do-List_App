package com.app.todolist

import android.app.Application
import android.util.Log
import androidx.work.Configuration


class PFAApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()

    }

    override fun getWorkManagerConfiguration() : Configuration {
        return Configuration.Builder().setMinimumLoggingLevel(Log.INFO).build();
    }
}