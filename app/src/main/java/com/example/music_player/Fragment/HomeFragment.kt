package com.example.music_player.Fragment

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.music_player.Adapter.MusicAdapter
import com.example.music_player.DB.MusicDao
import com.example.music_player.DB.MusicDatabase
import com.example.music_player.Interface.onItemClick
import com.example.music_player.Model.Music
import com.example.music_player.MusicViewModel.MusicViewModel
import com.example.music_player.R
import com.example.music_player.Repository.MusicRepository
import com.example.music_player.databinding.FragmentHomeBinding
import com.example.music_player.factorymodel.MusicFactory
import java.io.IOException

class HomeFragment : Fragment(),onItemClick {

    private var binding: FragmentHomeBinding?=null
    private  var musicList: ArrayList<Music>?=null
    private  var musicRecyclerView: RecyclerView?=null
    private  var musicAdapter: MusicAdapter?=null
    private  var mediaPlayer: MediaPlayer?=null
    private var music:Music?=null
    private var musicDao:MusicDao?=null
    private var musicDatabase: MusicDatabase?=null
    private var musicViewModel:MusicViewModel?=null
    private val REQUEST_CODE_STORAGE_PERMISSION = 100
    private val REQUEST_CODE_READ_EXTERNAL_STORAGE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        binding= FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        val db=MusicDatabase.getDataBase(requireContext())
        val musicRepository=MusicRepository(db)
        musicViewModel=ViewModelProvider(this,MusicFactory(musicRepository)).get(MusicViewModel::class.java)
        musicRecyclerView=binding?.recyclerview
        musicRecyclerView?.layoutManager = LinearLayoutManager(requireContext())

        musicList = ArrayList()
        musicAdapter = MusicAdapter(musicList!!, this)
        musicRecyclerView?.adapter = musicAdapter

        mediaPlayer = MediaPlayer()
        try {
            loadMusic()
        }
        catch (e:java.lang.Exception){
            Toast.makeText(requireContext(), "Error"+e.message, Toast.LENGTH_SHORT).show()
        }
        try {
            binding?.floatingActionButton?.setOnClickListener {
                findNavController().navigate(R.id.favouriteFragment)
            }

        }
        catch (e:Exception){
            Toast.makeText(requireContext(), "Error"+e.message, Toast.LENGTH_SHORT).show()
        }

//        binding?.searchmusic?.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                if (query!=null){
//                    search(query)
//                }
//                else{
//                    Toast.makeText(requireContext(), "No data Found", Toast.LENGTH_SHORT).show()
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                if (newText!=null)
//                    search(newText)
//                return true
//            }
//        })
        return binding?.root

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun loadMusic() {


        // Use content resolver to query for music files on device
        val musicCursor = context?.contentResolver?.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )


        if (musicCursor != null) {
            while (musicCursor.moveToNext()) {
                val musicName =
                    musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val musicArtist =
                    musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val musicAlbum =
                    musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC))
                val musicPath =
                    musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA))

                // Create Music object and add to list
                val music = Music(0,musicName, musicArtist, musicAlbum, musicPath)
                musicList?.add(music)
            }
            musicCursor.close()
            musicAdapter?.notifyDataSetChanged()
        }
    }

    fun search(name:String){
        val searchtext=binding?.searchmusic
        try {
            musicViewModel?.search(name)?.observe(this, Observer {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            })
        }
        catch (e:Exception){
            Toast.makeText(requireContext(), "Error Finding!! :"+e.message, Toast.LENGTH_SHORT).show()
        }
    }



//    override fun onMusicItemClick(position: Int) {
//        // Get selected music file
//        val music = musicList?.get(position)
//
//        // Play music file
//        try {
//            mediaPlayer?.reset()
//            mediaPlayer?.setDataSource(music?.path)
//            mediaPlayer?.prepare()
//            mediaPlayer?.start()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }

    override fun onShortClick(position: Int, music: Music) {
//        Toast.makeText(requireContext(), "onclick", Toast.LENGTH_SHORT).show()
        val name=music.name
        Toast.makeText(requireContext(), name, Toast.LENGTH_SHORT).show()
        val music = musicList?.get(position)

        // Play music file
        try {
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(music?.path)
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onLongClick(position: Int, music: Music) {
        try {
            val name=music.name
            val artist=music.artist
            val album=music.album
            val path=music.path

            val music=Music(0,name, artist, album, path)
            musicViewModel?.insertMusic(name, album, artist, path)
            Toast.makeText(requireContext(), "Data inserted", Toast.LENGTH_SHORT).show()


        }
        catch (e:Exception){
            Toast.makeText(requireContext(), "Error"+e.message, Toast.LENGTH_SHORT).show()
        }
    }



}