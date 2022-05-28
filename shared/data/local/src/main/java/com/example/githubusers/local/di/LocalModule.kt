package com.example.githubusers.local.di

import android.content.Context
import com.example.githubusers.data.user.LocalUserDataSource
import com.example.githubusers.local.db.AppDestructibleDatabase
import com.example.githubusers.local.user.DefaultLocalUserDataSource
import com.example.githubusers.local.user.RoomUserDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class LocalModule {

    @Singleton
    @Binds
    abstract fun provideLocalUserDataSource(dataSource: DefaultLocalUserDataSource): LocalUserDataSource

    companion object {
        @Singleton
        @Provides
        fun provideAppDestructibleDatabase(@ApplicationContext context: Context): AppDestructibleDatabase = AppDestructibleDatabase.buildDatabase(context)

        @Singleton
        @Provides
        fun provideUserDao(db: AppDestructibleDatabase): RoomUserDao = db.getUserDao()
    }
}