package com.example.githubusers.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubusers.data.user.favorite.LocalFavorite
import com.example.githubusers.data.user.search.LocalSearchUser
import com.example.githubusers.data.user.search.SearchUserRemotePage
import com.example.githubusers.local.user.RoomUserDao
import com.example.githubusers.local.user.detail.LocalDetailUser

@Database(
    entities = [
        LocalSearchUser::class,
        SearchUserRemotePage::class,
        LocalDetailUser::class,
        LocalFavorite::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDestructibleDatabase : RoomDatabase() {

    internal abstract fun getUserDao(): RoomUserDao

    companion object {
        private const val DATABASE_NAME = "room-destructible-db"

        fun buildDatabase(context: Context): AppDestructibleDatabase {
            return Room.databaseBuilder(context, AppDestructibleDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}