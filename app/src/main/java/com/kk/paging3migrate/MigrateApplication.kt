package com.kk.paging3migrate

import android.app.Application
import android.content.Context

/**
 * @author kuky.
 * @description
 */
class MigrateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
    }

    companion object {
        lateinit var ctx: Context
    }
}