package com.example.githubusers.thirdparty.di

import com.example.githubusers.ktutil.log.CrashReportHelper
import com.example.githubusers.ktutil.log.EventLogHelper
import com.example.githubusers.thirdparty.DefaultThirdPartyInitializer
import com.example.githubusers.thirdparty.ThirdPartyInitializer
import com.example.githubusers.thirdparty.crash.DefaultCrashReportHelper
import com.example.githubusers.thirdparty.log.DefaultEventLogHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class ThirdPartyModule {

    @Singleton
    @Binds
    abstract fun provideThirdPartyInitializer(helper: DefaultThirdPartyInitializer): ThirdPartyInitializer

    @Singleton
    @Binds
    abstract fun provideCrashReportHelper(helper: DefaultCrashReportHelper): CrashReportHelper

    @Singleton
    @Binds
    abstract fun provideEventLogHelper(helper: DefaultEventLogHelper): EventLogHelper
}