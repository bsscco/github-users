package com.example.githubusers.remote.api

import com.example.githubusers.remote.user.detail.GetUserResponse
import com.example.githubusers.remote.user.search.SearchUsersResponse

internal interface GithubApiService {

    suspend fun searchUsers(keyword: String, perPage: Int, page: Int): SearchUsersResponse

    suspend fun getUser(userName: String): GetUserResponse
}