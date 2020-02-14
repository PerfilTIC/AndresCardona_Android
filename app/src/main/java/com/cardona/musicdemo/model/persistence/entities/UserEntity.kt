package com.cardona.musicdemo.model.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
class UserEntity (

    @PrimaryKey
    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "email")
    val email: String? = null,

    @ColumnInfo(name = "followers")
    val followers: String? = null,

    @ColumnInfo(name = "country")
    val country: String? = null,

    @ColumnInfo(name = "product")
    val product: String? = null,

    @ColumnInfo(name = "photo")
    val photo: String? = null

)