package com.example.music_player.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.music_player.Fragment.HomeFragment
import com.example.music_player.Interface.onItemClick
import com.example.music_player.Model.Music
import com.example.music_player.databinding.ItemMusicBinding

class MusicAdapter(
    var musicList: ArrayList<Music>,
//    private val listener: OnMusicItemClickListener,
    private var onItemClick: onItemClick


) :
    RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

//    interface OnMusicItemClickListener {
//        fun onMusicItemClick(position: Int)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val binding = ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
      val   musicList = musicList[position]
        holder.binding.audiotitle.text = musicList.name
        holder.binding.artist.text = musicList.artist
        holder.binding.albumView.text = musicList.album
//        holder.itemView.setOnClickListener {
//            onclickinerface.onFavouriteUserClickListener(position,musicList)
//        }
        holder.itemView.setOnClickListener {
            onItemClick.onShortClick(position, musicList )
        }

        holder.itemView.setOnLongClickListener {
            onItemClick.onLongClick(position, musicList )

            true
        }

    }

    override fun getItemCount(): Int = musicList?.size!!

    inner class MusicViewHolder(val binding: ItemMusicBinding) : RecyclerView.ViewHolder(binding.root)
         {


//        init {
//            binding.root.setOnClickListener(this)
//
//        }

//        override fun onClick(v: View?) {
//            val position = adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                listener.onMusicItemClick(position)
//            }
//        }
    }
}
