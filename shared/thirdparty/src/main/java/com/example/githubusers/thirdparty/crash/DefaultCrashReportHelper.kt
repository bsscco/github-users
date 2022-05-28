package com.example.githubusers.thirdparty.crash

import com.example.githubusers.ktutil.log.CrashReportHelper
import com.example.githubusers.thirdparty.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

internal class DefaultCrashReportHelper @Inject constructor() : CrashReportHelper {

    override fun logAndReport(error: Throwable) {
        error.printStackTrace()
        if (BuildConfig.DEBUG.not()) {
            if (error.isJobCancellationException().not()) {
                FirebaseCrashlytics.getInstance().recordException(error)
            }
        }
    }

    private fun Throwable.isJobCancellationException(): Boolean {
        return this is CancellationException && message == "Job was cancelled"
    }
}