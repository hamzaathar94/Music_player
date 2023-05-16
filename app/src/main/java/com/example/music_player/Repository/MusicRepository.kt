package com.example.music_player.Repository

import androidx.lifecycle.LiveData
import com.example.music_player.DB.MusicDatabase
import com.example.music_player.Model.Music

class MusicRepository(private val musicDatabase: MusicDatabase) {
    fun getallMusic():LiveData<List<Music>>{
return musicDatabase.getMusicDao().getAllMusic()
    }


    suspend fun insertmusic(music: Music){
        musicDatabase.getMusicDao().insert(music)
    }

    fun deleteMusic(music: Music){
        musicDatabase.getMusicDao().deletemusic(music)
    }

    fun search(name:String):LiveData<List<Music>>{
        return musicDatabase.getMusicDao().search(name)
    }


}