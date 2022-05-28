package com.example.githubusers.remote.api

import com.example.githubusers.remote.user.detail.GetUserResponse
import com.example.githubusers.remote.user.search.SearchUsersResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

internal interface RetrofitGithubApiService {

    @GET("search/users")
    @Headers("Content-Type: application/json")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): SearchUsersResponse

    @GET("users/{userName}")
    @Headers("Content-Type: application/json")
    suspend fun getUser(@Path("userName") userName: String): GetUserResponse
}