package com.example.music_player.factorymodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.music_player.MusicViewModel.MusicViewModel
import com.example.music_player.Repository.MusicRepository

class MusicFactory(private val musicRepository: MusicRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MusicViewModel(musicRepository) as T
    }
}