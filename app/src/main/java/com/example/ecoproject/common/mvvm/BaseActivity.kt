package com.example.ecoproject.common.mvvm

import androidx.appcompat.app.AppCompatActivity
import com.example.ecoproject.app.App
import com.example.ecoproject.app.di.AppComponent

open class BaseActivity : AppCompatActivity() {
    val app: App by lazy {
        application as App
    }

    val component: AppComponent by lazy {
        app.component
    }


}