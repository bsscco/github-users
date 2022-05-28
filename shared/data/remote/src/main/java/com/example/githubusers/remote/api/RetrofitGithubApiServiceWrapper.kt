package com.example.githubusers.remote.api

import com.example.githubusers.remote.user.detail.GetUserResponse
import com.example.githubusers.remote.user.search.SearchUsersResponse

internal class RetrofitGithubApiServiceWrapper(
    private val retrofitGithubApiService: RetrofitGithubApiService,
) : GithubApiService {

    override suspend fun searchUsers(keyword: String, perPage: Int, page: Int): SearchUsersResponse {
        return retrofitGithubApiService.searchUsers(query = keyword, perPage, page)
    }

    override suspend fun getUser(userName: String): GetUserResponse {
        return retrofitGithubApiService.getUser(userName)
    }
}