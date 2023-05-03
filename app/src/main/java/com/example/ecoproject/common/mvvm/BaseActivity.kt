package com.example.ecoproject.common.mvvm

import androidx.appcompat.app.AppCompatActivity
import com.example.ecoproject.app.App
import com.example.ecoproject.data.di.DataComponent

open class BaseActivity : AppCompatActivity() {
    val app: App by lazy {
        application as App
    }

    val component: DataComponent by lazy {
        app.dataComponent
    }


}