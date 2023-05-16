package com.example.music_player.Fragment

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.music_player.Adapter.FavouriteAdapter
import com.example.music_player.Adapter.MusicAdapter
import com.example.music_player.DB.MusicDatabase
import com.example.music_player.Interface.onItemClick
import com.example.music_player.Model.Music
import com.example.music_player.MusicViewModel.MusicViewModel
import com.example.music_player.R
import com.example.music_player.Repository.MusicRepository
import com.example.music_player.databinding.FragmentFavouriteBinding
import com.example.music_player.factorymodel.MusicFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException


class FavouriteFragment : Fragment() ,onItemClick{

    private var binding: FragmentFavouriteBinding?=null
    private var recyclerView: RecyclerView?=null
    private var musicViewModel:MusicViewModel?=null
    private  var musicList: ArrayList<Music>?=null
    private  var mediaPlayer: MediaPlayer?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavouriteBinding.inflate(LayoutInflater.from(requireContext()),container,false)

        recyclerView= binding?.recyclerview
        recyclerView?.layoutManager= LinearLayoutManager(requireContext())
        val db= MusicDatabase.getDataBase(requireContext())
        val musicRepository= MusicRepository(db)
        musicViewModel= ViewModelProvider(this, MusicFactory(musicRepository)).get(MusicViewModel::class.java)
        musicViewModel?.music?.observe(viewLifecycleOwner, Observer {
            recyclerView?.adapter=FavouriteAdapter(requireContext(),it,this)

            mediaPlayer = MediaPlayer()
        })


        binding?.searchmusic?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!=null){
                    search(query)
                }
                else{
                    Toast.makeText(requireContext(), "No data Found", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!=null)
                    search(newText)
                return true
            }
        })

        return binding?.root

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

    override fun onShortClick(position: Int, music: Music) {
        try {
            val data=music.path
            //Toast.makeText(requireContext(), music.name, Toast.LENGTH_SHORT).show()
           // val name=music.name
            //Toast.makeText(requireContext(), name, Toast.LENGTH_SHORT).show()
           // val music = musicList?.get(position)

            // Play music file
            try {
                mediaPlayer?.reset()
                mediaPlayer?.setDataSource(data)
                mediaPlayer?.prepare()
                mediaPlayer?.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        catch (e:Exception){
            Toast.makeText(requireContext(), "Error!!"+e.message, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onLongClick(position: Int, music: Music) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirm Delete")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Delete") { dialog, which ->
                GlobalScope.launch{
                    musicViewModel?.deleteMusic(music)

                }
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // do nothing
            }

        val dialog = builder.create()
        dialog.show()
    }

//    override fun onSwipe(position: Int, music: Music) {
//        Toast.makeText(requireContext(), music.name, Toast.LENGTH_SHORT).show()
////        try {
////            val simpleCallback=object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
////                override fun onMove(
////                    recyclerView: RecyclerView,
////                    viewHolder: RecyclerView.ViewHolder,
////                    target: RecyclerView.ViewHolder
////                ): Boolean {
////                    return true
////                }
////
////
////                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
////                    val position=viewHolder.adapterPosition
////                    val note=music
////                    when(direction){
////                        ItemTouchHelper.RIGHT->{
////                            musicViewModel?.deleteMusic(note)
////                        }
////                        ItemTouchHelper.LEFT->{
////                            musicViewModel?.deleteMusic(note)
////                        }
////                    }
////
////
////                }
////            }
////        }
////        catch (e:Exception){
////            Toast.makeText(requireContext(), "Error!"+e.message, Toast.LENGTH_SHORT).show()
////        }
//    }





}