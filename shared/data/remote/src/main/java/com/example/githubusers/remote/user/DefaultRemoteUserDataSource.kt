package com.example.githubusers.remote.user

import com.example.githubusers.data.user.RemoteUserDataSource
import com.example.githubusers.data.user.detail.DataDetailUser
import com.example.githubusers.data.user.detail.DataNoDetailUserException
import com.example.githubusers.data.user.search.DataSearchUser
import com.example.githubusers.data.user.search.DataSearchUserRateLimitedPerMinuteException
import com.example.githubusers.data.user.search.DataSearchUserTimeOutException
import com.example.githubusers.remote.api.GithubApiService
import com.example.githubusers.remote.api.ProductionApiService
import com.example.githubusers.remote.user.detail.toData
import com.example.githubusers.remote.user.search.toData
import retrofit2.HttpException
import javax.inject.Inject

internal class DefaultRemoteUserDataSource @Inject constructor(
    @ProductionApiService private val githubApiService: GithubApiService,
) : RemoteUserDataSource {

    override suspend fun searchUsers(keyword: String, perPage: Int, page: Int): List<DataSearchUser> {
        val response = try {
            githubApiService.searchUsers(keyword, perPage, page)
        } catch (error: HttpException) {
            if (error.code() == 403) {
                throw DataSearchUserRateLimitedPerMinuteException("Remote rate limited")
            } else {
                throw error
            }
        }
        if (response.incompleteResults) {
            throw DataSearchUserTimeOutException("Remote timeout")
        }
        return response.toData()
    }

    override suspend fun getUser(userName: String): DataDetailUser {
        val response = try {
            githubApiService.getUser(userName)
        } catch (error: HttpException) {
            if (error.code() == 404) {
                throw DataNoDetailUserException("No remote user")
            } else {
                throw error
            }
        }
        return response.toData()
    }
}