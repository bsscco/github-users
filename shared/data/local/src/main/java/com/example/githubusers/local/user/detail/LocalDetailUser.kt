package com.example.githubusers.local.user.detail

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.githubusers.local.time.ZonedDateTimeTypeConverter
import org.threeten.bp.ZonedDateTime

@Keep
@Entity(tableName = "detailUsers")
@TypeConverters(ZonedDateTimeTypeConverter::class)
internal data class LocalDetailUser(
    @PrimaryKey val userName: String,
    val avatarUrl: String,
    val createdAt: ZonedDateTime,
)