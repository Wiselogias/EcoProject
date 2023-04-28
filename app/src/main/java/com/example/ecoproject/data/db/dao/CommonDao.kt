package com.example.ecoproject.data.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update

abstract class CommonDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(entity: T): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun update(entity: T): Int

    @Delete
    abstract suspend fun delete(entity: T): Int

    @Transaction
    open suspend fun upsert(entity: T): Int {
        val a = insert(entity)
        if (a == -1L)
            return update(entity)
        return a.toInt()
    }

}