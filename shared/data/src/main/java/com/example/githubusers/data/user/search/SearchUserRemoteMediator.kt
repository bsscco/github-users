package com.example.githubusers.data.user.search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.githubusers.data.user.LocalUserDataSource
import com.example.githubusers.data.user.RemoteUserDataSource
import com.example.githubusers.domain.user.search.SearchUserRateLimitedPerMinuteException
import com.example.githubusers.domain.user.search.SearchUserTimeOutException
import com.example.githubusers.ktutil.NoAction

@OptIn(ExperimentalPagingApi::class)
class SearchUserRemoteMediator(
    private val localUserDataSource: LocalUserDataSource,
    private val remoteUserDataSource: RemoteUserDataSource,
    private val keyword: String,
) : RemoteMediator<Int, SearchUserAndFavorite>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchUserAndFavorite>,
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> localUserDataSource.getSearchUserNextRemotePage()
        }

        return try {
            val remoteUsers = remoteUserDataSource.searchUsers(
                keyword = keyword,
                perPage = state.config.pageSize,
                page = page,
            )
            when (loadType) {
                LoadType.REFRESH -> localUserDataSource.setSearchUsers(remoteUsers, nextPage = 2)
                LoadType.PREPEND -> NoAction
                LoadType.APPEND -> localUserDataSource.addSearchUsers(remoteUsers, nextPage = page + 1)
            }

            val endOfPaginationReached = remoteUsers.isEmpty()
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: Exception) {
            when (error) {
                is DataSearchUserRateLimitedPerMinuteException -> MediatorResult.Error(SearchUserRateLimitedPerMinuteException)
                is DataSearchUserTimeOutException -> MediatorResult.Error(SearchUserTimeOutException)
                else -> MediatorResult.Error(error)
            }
        }
    }
}