package com.example.ecoproject.domain.repositories

import com.example.ecoproject.domain.entities.UserEntity

interface UserRepo {
    suspend fun getUser(id: String): UserEntity
    suspend fun createUser(user: UserEntity): UserEntity
}