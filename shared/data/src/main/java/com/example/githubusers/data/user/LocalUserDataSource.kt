package com.example.githubusers.data.user

import androidx.paging.PagingSource
import com.example.githubusers.data.user.detail.DataDetailUser
import com.example.githubusers.data.user.favorite.DataFavorite
import com.example.githubusers.data.user.search.DataSearchUser
import com.example.githubusers.data.user.search.SearchUserAndFavorite
import com.example.githubusers.ktutil.flow.Result
import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {

    suspend fun hasAnySearchUser(): Boolean

    fun getSearchUsers(): PagingSource<Int, SearchUserAndFavorite>

    suspend fun setSearchUsers(users: List<DataSearchUser>, nextPage: Int)

    suspend fun addSearchUsers(users: List<DataSearchUser>, nextPage: Int)

    suspend fun getSearchUserNextRemotePage(): Int

    suspend fun hasDetailUser(userName: String): Boolean

    fun getDetailUser(userName: String): Flow<Result<Pair<DataDetailUser, DataFavorite?>>>

    suspend fun setDetailUser(user: DataDetailUser)

    fun getFavorites(): Flow<Result<List<DataFavorite>>>

    fun searchFavorites(keyword: String): Flow<Result<List<DataFavorite>>>

    suspend fun addFavorite(userName: String, avatarUrl: String)

    suspend fun removeFavorite(userName: String)
}