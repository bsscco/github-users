package com.example.githubusers.local.user

import androidx.paging.PagingSource
import com.example.githubusers.data.user.LocalUserDataSource
import com.example.githubusers.data.user.detail.DataDetailUser
import com.example.githubusers.data.user.favorite.DataFavorite
import com.example.githubusers.data.user.favorite.LocalFavorite
import com.example.githubusers.data.user.search.DataSearchUser
import com.example.githubusers.data.user.search.SearchUserAndFavorite
import com.example.githubusers.ktutil.flow.Result
import com.example.githubusers.ktutil.flow.withResult
import com.example.githubusers.local.user.detail.toData
import com.example.githubusers.local.user.detail.toLocal
import com.example.githubusers.local.user.favorite.toData
import com.example.githubusers.local.user.search.toLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultLocalUserDataSource @Inject constructor(
    private val roomUserDao: RoomUserDao,
) : LocalUserDataSource {

    override suspend fun hasAnySearchUser(): Boolean {
        return roomUserDao.hasAnySearchUser()
    }

    override fun getSearchUsers(): PagingSource<Int, SearchUserAndFavorite> {
        return roomUserDao.getSearchUsers()
    }

    override suspend fun setSearchUsers(users: List<DataSearchUser>, nextPage: Int) {
        roomUserDao.setSearchUsers(
            users = users.map { it.toLocal() },
            nextPage = nextPage,
        )
    }

    override suspend fun addSearchUsers(users: List<DataSearchUser>, nextPage: Int) {
        roomUserDao.addSearchUsers(
            users = users.map { it.toLocal() },
            nextPage = nextPage,
        )
    }

    override suspend fun getSearchUserNextRemotePage(): Int {
        return roomUserDao.getSearchUserRemotePage().nextPage
    }

    override suspend fun hasDetailUser(userName: String): Boolean {
        return roomUserDao.hasDetailUser(userName)
    }

    override fun getDetailUser(userName: String): Flow<Result<Pair<DataDetailUser, DataFavorite?>>> {
        return roomUserDao.getDetailUser(userName)
            .filterNotNull()
            .map { user ->
                withResult { user.toData() }
            }
    }

    override suspend fun setDetailUser(user: DataDetailUser) {
        roomUserDao.insertDetailUser(user.toLocal())
    }

    override fun getFavorites(): Flow<Result<List<DataFavorite>>> {
        return roomUserDao.getFavorites()
            .map { favorites ->
                withResult { favorites.map { it.toData() } }
            }
    }

    override fun searchFavorites(keyword: String): Flow<Result<List<DataFavorite>>> {
        return roomUserDao.searchFavorites(keyword)
            .map { favorites ->
                withResult { favorites.map { it.toData() } }
            }
    }

    override suspend fun addFavorite(userName: String, avatarUrl: String) {
        roomUserDao.insertFavorite(LocalFavorite(userName, avatarUrl))
    }

    override suspend fun removeFavorite(userName: String) {
        roomUserDao.deleteFavorite(userName)
    }
}