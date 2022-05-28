package com.example.githubusers.data.user

import com.example.githubusers.data.user.detail.DataDetailUser
import com.example.githubusers.data.user.search.DataSearchUser

interface RemoteUserDataSource {

    suspend fun searchUsers(keyword: String, perPage: Int, page: Int): List<DataSearchUser>

    suspend fun getUser(userName: String): DataDetailUser
}