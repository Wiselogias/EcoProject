package com.example.ecoproject.app

import android.app.Application
import com.example.ecoproject.app.di.AppComponent
import com.example.ecoproject.app.di.AppModule
import com.example.ecoproject.app.di.DaggerAppComponent

class App : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}