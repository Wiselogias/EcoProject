package com.example.ecoproject.app

import android.app.Application
import com.example.ecoproject.app.di.AppComponent
import com.example.ecoproject.app.di.AppModule
import com.example.ecoproject.app.di.DaggerAppComponent
import com.example.ecoproject.data.di.DaggerDataComponent
import com.example.ecoproject.data.di.DataComponent

class App : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
    val dataComponent: DataComponent by lazy {
        DaggerDataComponent.builder().appComponent(appComponent).build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}