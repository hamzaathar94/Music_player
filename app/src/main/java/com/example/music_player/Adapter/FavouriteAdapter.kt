package com.example.music_player.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.music_player.Interface.onItemClick
import com.example.music_player.Model.Music
import com.example.music_player.databinding.ItemMusicBinding

class FavouriteAdapter(val context: Context, val musicList:List<Music>,var onItemClick: onItemClick):RecyclerView.Adapter<FavouriteAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteAdapter.MyViewHolder {
        val binding = ItemMusicBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteAdapter.MyViewHolder, position: Int) {
        val data= musicList[position]
        holder.binding.audiotitle.text=data.name
        holder.binding.albumView.text=data.album
        holder.binding.artist.text=data.artist
        holder.itemView.setOnClickListener {
            onItemClick.onShortClick(position,data)
        }
        holder.itemView.setOnLongClickListener {
            onItemClick.onLongClick(position,data)
            true
        }

    }

    override fun getItemCount(): Int {
        return musicList.size
    }
    inner class MyViewHolder(var binding:ItemMusicBinding):RecyclerView.ViewHolder(binding.root){

    }

}