package com.example.githubusers.local.user

import androidx.paging.PagingSource
import androidx.room.*
import com.example.githubusers.data.user.favorite.LocalFavorite
import com.example.githubusers.data.user.search.LocalSearchUser
import com.example.githubusers.data.user.search.SearchUserAndFavorite
import com.example.githubusers.data.user.search.SearchUserRemotePage
import com.example.githubusers.local.user.detail.DetailUserAndFavorite
import com.example.githubusers.local.user.detail.LocalDetailUser
import kotlinx.coroutines.flow.Flow

@Dao
internal interface RoomUserDao {

    @Query("SELECT COUNT(*) FROM searchUsers LIMIT 1")
    suspend fun hasAnySearchUser(): Boolean

    @Query("SELECT * FROM searchUsers")
    fun getSearchUsers(): PagingSource<Int, SearchUserAndFavorite>

    @Transaction
    suspend fun setSearchUsers(users: List<LocalSearchUser>, nextPage: Int) {
        clearSearchUsers()
        clearSearchUserRemotePage()
        insertSearchUsers(users)
        insertSearchUserRemotePage(SearchUserRemotePage(nextPage))
    }

    @Query("DELETE FROM searchUsers")
    suspend fun clearSearchUsers()

    @Query("DELETE FROM searchUserRemotePage")
    suspend fun clearSearchUserRemotePage()

    @Insert
    suspend fun insertSearchUsers(users: List<LocalSearchUser>)

    @Insert
    suspend fun insertSearchUserRemotePage(page: SearchUserRemotePage)

    @Transaction
    suspend fun addSearchUsers(users: List<LocalSearchUser>, nextPage: Int) {
        insertSearchUsers(users)
        clearSearchUserRemotePage()
        insertSearchUserRemotePage(SearchUserRemotePage(nextPage))
    }

    @Query("SELECT * FROM searchUserRemotePage LIMIT 1")
    suspend fun getSearchUserRemotePage(): SearchUserRemotePage

    @Query("SELECT COUNT(*) FROM detailUsers WHERE userName = :userName")
    suspend fun hasDetailUser(userName: String): Boolean

    @Query("SELECT * FROM detailUsers WHERE userName = :userName LIMIT 1")
    fun getDetailUser(userName: String): Flow<DetailUserAndFavorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailUser(user: LocalDetailUser)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<LocalFavorite>>

    @Query("SELECT * FROM favorites WHERE userName LIKE '%' || :keyword || '%' ")
    fun searchFavorites(keyword: String): Flow<List<LocalFavorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: LocalFavorite)

    @Query("DELETE FROM favorites WHERE userName = :userName")
    suspend fun deleteFavorite(userName: String)
}