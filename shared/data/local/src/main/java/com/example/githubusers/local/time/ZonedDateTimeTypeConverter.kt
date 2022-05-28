package com.example.githubusers.local.time

import androidx.room.TypeConverter
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

internal class ZonedDateTimeTypeConverter {
    @TypeConverter
    fun toZonedDateTime(value: String?): ZonedDateTime? = value?.let { ZonedDateTime.parse(value) }

    @TypeConverter
    fun toString(zonedDateTime: ZonedDateTime?) = zonedDateTime?.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}