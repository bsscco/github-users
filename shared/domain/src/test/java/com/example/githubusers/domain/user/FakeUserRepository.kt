package com.example.githubusers.domain.user

import androidx.paging.PagingData
import com.example.githubusers.domain.user.detail.DetailUser
import com.example.githubusers.domain.user.favorite.Favorite
import com.example.githubusers.domain.user.search.SearchUser
import com.example.githubusers.ktutil.flow.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeUserRepository(
    val searchUsers: List<SearchUser> = emptyList(),
    val detailUsers: List<DetailUser> = emptyList(),
    val favorites: MutableList<Favorite> = mutableListOf(),
) : UserRepository {

    override fun getSearchUsers(refreshFromRemote: Boolean, keyword: String): Flow<PagingData<SearchUser>> {
        val filteredUsers = searchUsers.filter { it.userName.contains(keyword) }
        return flowOf(PagingData.from(filteredUsers))
    }

    override fun getDetailUser(refreshFromRemote: Boolean, userName: String): Flow<Result<DetailUser>> {
        val foundUser = detailUsers.first { it.userName == userName }
        return flowOf(Result.Success(foundUser))
    }

    override fun getFavorites(): Flow<Result<List<Favorite>>> {
        return flowOf(Result.Success(favorites))
    }

    override fun searchFavorites(keyword: String): Flow<Result<List<Favorite>>> {
        val filteredFavorites = favorites.filter { it.userName.contains(keyword) }
        return flowOf(Result.Success(filteredFavorites))
    }

    override suspend fun addFavorite(userName: String, avatarUrl: String) {
        favorites.add(Favorite(userName, avatarUrl))
    }

    override suspend fun removeFavorite(userName: String) {
        favorites.removeIf { it.userName == userName }
    }
}