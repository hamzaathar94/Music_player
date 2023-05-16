package com.example.music_player.MusicViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.music_player.Model.Music
import com.example.music_player.Repository.MusicRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MusicViewModel(private val musicRepository: MusicRepository) : ViewModel() {
    val music: LiveData<List<Music>> = musicRepository.getallMusic()

    fun insertMusic(name: String, album: String, artist: String, path: String) {
        GlobalScope.launch {
            val music = Music(null, name, artist, album, path)
            musicRepository.insertmusic(music)
        }


    }

    fun deleteMusic(music: Music){
        GlobalScope.launch {
            musicRepository.deleteMusic(music)
        }
    }

    fun search(name:String):LiveData<List<Music>>{
        return musicRepository.search(name)
    }
}