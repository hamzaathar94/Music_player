package com.example.music_player.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music")
data class Music(
    @PrimaryKey (autoGenerate = true)
    val id : Int?,
    val name: String,
                 val artist: String,
                 val album: String,
                 val path: String
                 )
