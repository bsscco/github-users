package com.example.githubusers.ktutil.log

interface CrashReportHelper {

    fun logAndReport(error: Throwable)
}