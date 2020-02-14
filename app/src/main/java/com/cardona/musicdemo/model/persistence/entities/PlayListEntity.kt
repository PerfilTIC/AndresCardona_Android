package com.cardona.musicdemo.model.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "play_list_table")
class PlayListEntity (

    @PrimaryKey
    @ColumnInfo(name = "playListId")
    val playListId: String,

    @ColumnInfo(name = "playListName")
    val playListName: String,

    @ColumnInfo(name = "followers")
    val followers: String? = null,

    @ColumnInfo(name = "songNames")
    val songNames: List<String?>,

    @ColumnInfo(name = "artists")
    val artists: List<String?>,

    @ColumnInfo(name = "durations")
    val durations: List<String?>,

    @ColumnInfo(name = "popularities")
    val popularities: List<String?>,

    @ColumnInfo(name = "songUris")
    val songUris: List<String?>,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?

)