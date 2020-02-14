package com.cardona.musicdemo.model.persistence.methods

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cardona.musicdemo.model.persistence.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(user: UserEntity)

    @Query("DELETE FROM users_table")
    suspend fun deleteUsers()

    @Query("SELECT * FROM users_table")
    fun getUsers(): LiveData<UserEntity>

}