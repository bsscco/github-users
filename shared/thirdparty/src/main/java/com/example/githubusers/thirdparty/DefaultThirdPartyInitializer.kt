package com.example.githubusers.thirdparty

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.githubusers.thirdparty.crash.CrashlyticsInitializer
import com.example.githubusers.thirdparty.log.FirebaseAnalyticsInitializer
import javax.inject.Inject

internal class DefaultThirdPartyInitializer @Inject constructor(
    private val firebaseAnalyticsInitializer: FirebaseAnalyticsInitializer,
    private val crashlyticsInitializer: CrashlyticsInitializer,
) : ThirdPartyInitializer, DefaultLifecycleObserver {

    override fun onApplicationCreate() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        firebaseAnalyticsInitializer.initSdk()
        crashlyticsInitializer.initSdk()
    }
}