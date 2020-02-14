package com.cardona.musicdemo.model.persistence.methods

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cardona.musicdemo.model.persistence.entities.PlayListEntity

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayList(playListEntity: PlayListEntity)

    @Query("DELETE FROM play_list_table")
    suspend fun deletePlayList()

    @Query("SELECT * FROM play_list_table")
    fun getPlayList(): LiveData<List<PlayListEntity>>

}