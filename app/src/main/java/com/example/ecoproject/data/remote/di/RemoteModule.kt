package com.example.ecoproject.data.remote.di

import com.example.ecoproject.data.di.DataScope
import com.example.ecoproject.data.remote.api.AnalyseApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class RemoteModule {
    @DataScope
    @Provides
    @Named("server-url")
    fun provideServerUrl(): String = "http://localhost:8080/" //TODO("Replace with real server url")

    @Provides
    @DataScope
    fun provideGson(): Gson = Gson()

    @Provides
    @DataScope
    fun provideRetrofit(@Named("server-url") url: String, gson: Gson): Retrofit =
        Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @DataScope
    fun provideAnalyseApi(retrofit: Retrofit): AnalyseApi = retrofit.create(AnalyseApi::class.java)
}