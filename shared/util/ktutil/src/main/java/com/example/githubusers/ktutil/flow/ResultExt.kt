package com.example.githubusers.ktutil.flow

import kotlinx.coroutines.flow.*

suspend fun <R> withResult(action: suspend () -> R) = try {
    Result.Success(action.invoke())
} catch (error: Exception) {
    Result.Error(error)
}

fun <T> Flow<Result<T>>.onErrorOrCatch(action: suspend (Throwable) -> Unit): Flow<Result<T>> {
    return this
        .onEach { if (it is Result.Error) action.invoke(it.exception) }
        .catch { throwable -> action.invoke(throwable) }
}

fun <T> Flow<Result<T>>.throwIfError(): Flow<Result<T>> {
    return onEach { if (it is Result.Error) throw it.exception }
}

fun <T> Flow<Result<T>>.onSuccess(action: suspend (T) -> Unit): Flow<Result<T>> {
    return onEach { if (it is Result.Success) action.invoke(it.data) }
}

fun <T> Flow<Result<T>>.filterSuccess() = filterIsInstance<Result.Success<T>>()

fun <T, R> Flow<Result<T>>.mapSuccessData(action: suspend (T) -> Result<R>): Flow<Result<R>> {
    return map {
        when (it) {
            is Result.Error -> Result.Error(it.exception)
            is Result.Success -> action.invoke(it.data)
            is Result.Loading -> Result.Loading
        }
    }
}

fun <T> Flow<Result.Success<T>>.mapToData(): Flow<T> = map { it.data }

fun <T> Result<T>.successOrThrow(): T {
    return when (this) {
        is Result.Success -> this.data
        is Result.Loading -> throw Exception("Loading can not be cast as Success.")
        is Result.Error -> throw this.exception
    }
}

suspend fun <T> Flow<Result<T>>.firstSuccessOrThrow(): T {
    return throwIfError()
        .filterSuccess()
        .take(1)
        .mapToData()
        .single()
}