package com.example.ecoproject.data.db.database

import androidx.room.TypeConverter
import org.joda.time.DateTime

class DateTimeTypeConverter{
    @TypeConverter
    fun fromDateTimeToLong(dateTime: DateTime?) = dateTime?.millis ?: -1
    @TypeConverter
    fun fromLongToDateTime(millis: Long) =
        if(millis == -1L) null
        else DateTime(millis)
}