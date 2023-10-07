package com.example.bookkmm.android

import android.app.Application
import com.example.bookkmm.android.di.androidModule
import com.example.bookkmm.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class BookApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BookApp)
            androidLogger()
            modules(appModule() + androidModule)
        }
    }
}

