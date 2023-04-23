package com.example.ecoproject.data.db.dao

import androidx.room.*

abstract class CommonDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(entity: T): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun update(entity: T): Long

    @Delete
    abstract suspend fun delete(entity: T): Long

    @Transaction
    open suspend fun upsert(entity: T): Long {
        val a = insert(entity)
        if (a == -1L)
            return update(entity)
        return a
    }

}