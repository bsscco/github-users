package com.example.githubusers.usersearch

import androidx.paging.PagingData
import androidx.paging.map
import com.example.githubusers.domain.user.search.SearchUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal fun Flow<PagingData<SearchUser>>.toUserListState() = UserSearchContract.State.UserListState.UserList(
    users = map { pagingData ->
        pagingData.map { user ->
            UserSearchContract.State.UserListState.UserList.User(
                userName = user.userName,
                avatarUrl = user.avatarUrl,
                favorite = user.favorite,
            )
        }
    }
)
