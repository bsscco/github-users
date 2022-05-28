package com.example.githubusers.data.user

import androidx.paging.*
import com.example.githubusers.data.user.detail.DataNoDetailUserException
import com.example.githubusers.data.user.detail.toDomain
import com.example.githubusers.data.user.favorite.DataFavorite
import com.example.githubusers.data.user.favorite.toDomain
import com.example.githubusers.data.user.search.SearchUserRemoteMediator
import com.example.githubusers.data.user.search.toDomain
import com.example.githubusers.domain.user.UserRepository
import com.example.githubusers.domain.user.detail.DetailUser
import com.example.githubusers.domain.user.detail.NoDetailUserException
import com.example.githubusers.domain.user.favorite.Favorite
import com.example.githubusers.domain.user.search.SearchUser
import com.example.githubusers.ktutil.flow.Result
import com.example.githubusers.ktutil.flow.mapSuccessData
import com.example.githubusers.ktutil.flow.withResult
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class DefaultUserRepository @Inject constructor(
    private val localUserDataSource: LocalUserDataSource,
    private val remoteUserDataSource: RemoteUserDataSource,
) : UserRepository {

    override fun getSearchUsers(
        refreshFromRemote: Boolean,
        keyword: String,
    ): Flow<PagingData<SearchUser>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
            ),
            remoteMediator = SearchUserRemoteMediator(localUserDataSource, remoteUserDataSource, keyword),
            pagingSourceFactory = { localUserDataSource.getSearchUsers() }
        ).flow.map { pagingData ->
            pagingData.map { userAndFavorite ->
                userAndFavorite.toDomain()
            }
        }
    }

    override fun getDetailUser(
        refreshFromRemote: Boolean,
        userName: String,
    ): Flow<Result<DetailUser>> {
        return flow {
            if (refreshFromRemote || localUserDataSource.hasDetailUser(userName).not()) {
                val remoteUser = remoteUserDataSource.getUser(userName)
                localUserDataSource.setDetailUser(remoteUser)
            }
            emitAll(localUserDataSource.getDetailUser(userName))
        }
            .mapSuccessData { localUser ->
                withResult { localUser.toDomain() }
            }
            .catch { error ->
                when (error) {
                    is DataNoDetailUserException -> throw NoDetailUserException
                    else -> throw error
                }
            }
    }

    override fun getFavorites(): Flow<Result<List<Favorite>>> {
        return localUserDataSource.getFavorites()
            .mapSuccessData { favorites ->
                withResult { favorites.map(DataFavorite::toDomain) }
            }
    }

    override fun searchFavorites(keyword: String): Flow<Result<List<Favorite>>> {
        return localUserDataSource.searchFavorites(keyword)
            .mapSuccessData { favorites ->
                withResult { favorites.map(DataFavorite::toDomain) }
            }
    }

    override suspend fun addFavorite(userName: String, avatarUrl: String) {
        localUserDataSource.addFavorite(userName, avatarUrl)
    }

    override suspend fun removeFavorite(userName: String) {
        localUserDataSource.removeFavorite(userName)
    }
}