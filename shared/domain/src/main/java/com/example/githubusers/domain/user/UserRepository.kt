package com.example.githubusers.domain.user

import androidx.paging.PagingData
import com.example.githubusers.domain.user.detail.DetailUser
import com.example.githubusers.domain.user.favorite.Favorite
import com.example.githubusers.domain.user.search.SearchUser
import com.example.githubusers.ktutil.flow.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getSearchUsers(
        refreshFromRemote: Boolean,
        keyword: String,
    ): Flow<PagingData<SearchUser>>

    fun getDetailUser(
        refreshFromRemote: Boolean,
        userName: String,
    ): Flow<Result<DetailUser>>

    fun getFavorites(): Flow<Result<List<Favorite>>>

    fun searchFavorites(keyword: String): Flow<Result<List<Favorite>>>

    suspend fun addFavorite(userName: String, avatarUrl: String)

    suspend fun removeFavorite(userName: String)
}