package com.example.githubusers.remote.user

import com.example.githubusers.remote.api.GithubApiService
import com.example.githubusers.remote.user.detail.GetUserResponse
import com.example.githubusers.remote.user.search.SearchUsersResponse
import retrofit2.HttpException

internal class FakeGithubApiService(
    val httpException: HttpException? = null,
    val searchUsersResponse: SearchUsersResponse? = null,
    val getUserResponse: GetUserResponse? = null,
) : GithubApiService {

    override suspend fun searchUsers(keyword: String, perPage: Int, page: Int): SearchUsersResponse {
        if (httpException != null) {
            throw httpException
        }
        return searchUsersResponse!!
    }

    override suspend fun getUser(userName: String): GetUserResponse {
        if (httpException != null) {
            throw httpException
        }
        return getUserResponse!!
    }
}