package com.example.githubusers.app

import android.app.Application
import com.example.githubusers.thirdparty.ThirdPartyInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GithubUsersApplication : Application() {

    @Inject
    lateinit var thirdPartyInitializer: ThirdPartyInitializer

    override fun onCreate() {
        super.onCreate()
        thirdPartyInitializer.onApplicationCreate()
    }
}