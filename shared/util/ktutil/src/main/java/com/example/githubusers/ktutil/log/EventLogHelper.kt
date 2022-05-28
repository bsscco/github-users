package com.example.githubusers.ktutil.log

interface EventLogHelper {

    fun logEvent(event: String, params: Map<String, Any?> = emptyMap())
}