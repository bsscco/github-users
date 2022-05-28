package com.example.githubusers.data.di

import com.example.githubusers.data.user.DefaultUserRepository
import com.example.githubusers.domain.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataModule {

    @Singleton
    @Binds
    abstract fun provideUserRepository(repository: DefaultUserRepository): UserRepository
}