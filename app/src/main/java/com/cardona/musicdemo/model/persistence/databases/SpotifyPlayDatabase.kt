package com.cardona.musicdemo.model.persistence.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cardona.musicdemo.model.persistence.TypeConverters.ListTypeConverter
import com.cardona.musicdemo.model.persistence.entities.PlayListEntity
import com.cardona.musicdemo.model.persistence.entities.UserEntity
import com.cardona.musicdemo.model.persistence.methods.ListDao
import com.cardona.musicdemo.model.persistence.methods.UserDao

@Database(entities = [
    UserEntity::class,
    PlayListEntity::class
], version = 1, exportSchema = false)
@TypeConverters(ListTypeConverter::class)
abstract class SpotifyPlayDatabase: RoomDatabase() {

    abstract fun listDao() : ListDao
    abstract fun usersDao() : UserDao

}