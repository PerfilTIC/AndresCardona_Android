package com.cardona.musicdemo.view.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cardona.musicdemo.R
import com.cardona.musicdemo.model.dto.playlist.ItemsItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.playlist_item.view.*

class PlayListAdapter(val context: Context): RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder>() {

    private var playLists: List<ItemsItem?>? = null

    fun addPlayLists(itemsItem: List<ItemsItem?>?) {
        playLists = itemsItem
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        return PlayListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false))
    }

    override fun getItemCount(): Int {
        return playLists?.size ?: 0
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {

        holder.bind(playLists?.get(position))

        holder.itemView.setOnClickListener {

        }

    }

    inner class PlayListViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bind(item: ItemsItem?){
            itemView.tv_playlist.text = item?.name
            Glide.with(context).load(item?.images?.get(0)?.url).into(itemView.iv_playlist)
        }

    }

}