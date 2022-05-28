package com.example.githubusers.thirdparty.log

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.Size
import androidx.core.os.bundleOf
import com.example.githubusers.ktutil.log.EventLogHelper
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class DefaultEventLogHelper @Inject constructor(
    @ApplicationContext private val context: Context,
) : EventLogHelper {

    @SuppressLint("MissingPermission")
    override fun logEvent(
        @Size(min = 1L, max = 40L) event: String,
        params: Map<String, Any?>,
    ) {
        val bundle = bundleOf(*params.map { (k, v) -> k to v }.toTypedArray())
        FirebaseAnalytics.getInstance(context).logEvent(event, bundle)
    }
}