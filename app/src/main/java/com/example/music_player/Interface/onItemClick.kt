package com.example.music_player.Interface

import com.example.music_player.Model.Music

interface onItemClick {
    fun onShortClick(position: Int, music: Music)
    fun onLongClick(position: Int, music: Music)
}