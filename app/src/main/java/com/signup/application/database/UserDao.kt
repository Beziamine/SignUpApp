package com.signup.application.database

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :userId")
    fun findUserById(userId: Int): UserEntity

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<UserEntity>

}