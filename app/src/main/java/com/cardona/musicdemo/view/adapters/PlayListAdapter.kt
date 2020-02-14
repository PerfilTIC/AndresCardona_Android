package com.cardona.musicdemo.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.cardona.musicdemo.R
import com.cardona.musicdemo.model.dto.playList.ItemsItem
import kotlinx.android.synthetic.main.songs_list_item.view.*
import java.util.concurrent.TimeUnit

class PlayListAdapter(
    private val context: Context,
    private val navController: NavController
) : RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder>() {

    private var tracks: MutableList<ItemsItem?>? = mutableListOf()

    fun addPlayLists(itemsItem: MutableList<ItemsItem?>?) {

        itemsItem?.forEach { element ->
            tracks?.add(element)
        }

        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        tracks?.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItem(position: Int): ItemsItem?{
        return tracks?.get(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        return PlayListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.songs_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return tracks?.size ?: 0
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {

        holder.itemView.tv_songName.text = tracks?.get(position)?.track?.name
        holder.itemView.tv_author.text = tracks?.get(position)?.track?.artists?.get(0)?.name

        holder.itemView.tv_duration.run {
            val milliSeconds = tracks?.get(position)?.track?.durationMs?.toLong()
            milliSeconds?.let {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(it)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(it-1000*(minutes*60))
                text = "$minutes:$seconds"
            }
        }

        val popularity = tracks?.get(position)?.track?.popularity
        holder.itemView.ratingBar.rating = popularity?.div(100.toFloat()) ?: 0.toFloat()

    }

    inner class PlayListViewHolder(view: View) : RecyclerView.ViewHolder(view)
}