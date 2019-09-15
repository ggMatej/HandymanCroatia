package hr.ferit.matejmijic.handymancroatia

import android.app.Application
import android.content.Context

class HandymanApp : Application() {
    companion object {
        lateinit var instance: HandymanApp
            private set

        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this


    }
}