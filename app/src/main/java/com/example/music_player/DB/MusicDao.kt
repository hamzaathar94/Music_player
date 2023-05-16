package com.example.music_player.DB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.music_player.Model.Music

@Dao
interface MusicDao {

    @Insert
    fun insert(music: Music)

    @Query("SELECT * FROM music" )
   fun getAllMusic():LiveData<List<Music>>

    @Delete
    fun deletemusic(music: Music)

    @Query("SELECT * FROM music WHERE name LIKE :name ")
    fun search(name:String):LiveData<List<Music>>
}