package com.example.music_player.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.music_player.Model.Music

@Database(entities = [(Music::class)], version = 1, exportSchema = false)
abstract class MusicDatabase:RoomDatabase() {
    abstract fun getMusicDao(): MusicDao

    companion object{
        private var INSTANCE:MusicDatabase?=null

        fun getDataBase(context: Context):MusicDatabase{
            if (INSTANCE==null){
                synchronized(this){
                    INSTANCE=Room.databaseBuilder(
                        context,
                        MusicDatabase::class.java,
                        "Music_database"
                   ).build()
                }
            }
            return INSTANCE!!
        }
   }
}