package com.example.githubusers.remote.di

import com.example.githubusers.data.user.RemoteUserDataSource
import com.example.githubusers.remote.BuildConfig
import com.example.githubusers.remote.api.*
import com.example.githubusers.remote.user.DefaultRemoteUserDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class RemoteModule {

    @Singleton
    @Binds
    abstract fun provideRemoteUserDataSource(dataSource: DefaultRemoteUserDataSource): RemoteUserDataSource

    companion object {
        @Singleton
        @ProductionApiService
        @Provides
        fun provideProductionGithubApiService(): GithubApiService = RetrofitGithubApiServiceWrapper(
            Retrofit.Builder()
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build()
                .create(RetrofitGithubApiService::class.java)
        )

        @Singleton
        @DevApiService
        @Provides
        fun provideDevGithubApiService(): GithubApiService = RetrofitGithubApiServiceWrapper(
            Retrofit.Builder()
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build()
                .create(RetrofitGithubApiService::class.java)
        )

        private fun createHttpClient() = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
            )
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}