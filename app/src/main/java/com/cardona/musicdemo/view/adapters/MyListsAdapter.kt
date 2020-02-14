package com.cardona.musicdemo.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cardona.musicdemo.R
import com.cardona.musicdemo.model.dto.myLists.ItemsItem
import com.cardona.musicdemo.view.fragments.UserInfoFragmentDirections
import kotlinx.android.synthetic.main.playlist_item.view.*

class MyListsAdapter(
    private val context: Context,
    private val navController: NavController
): RecyclerView.Adapter<MyListsAdapter.MyListsViewHolder>() {

    private var playLists: List<ItemsItem?>? = null

    fun addPlayLists(itemsItem: List<ItemsItem?>?) {
        playLists = itemsItem
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListsViewHolder {
        return MyListsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false))
    }

    override fun getItemCount(): Int {
        return playLists?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyListsViewHolder, position: Int) {

        holder.bind(playLists?.get(position))

        val id = playLists?.get(position)?.id

        val action = id?.let {
            UserInfoFragmentDirections.actionUserInfoFragmentToPlayListFragment(
                it
            )
        }

        holder.itemView.setOnClickListener {
            action?.let { it1 -> navController.navigate(it1) }
        }

    }

    inner class MyListsViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bind(item: ItemsItem?){
            itemView.tv_playlist.text = item?.name
            Glide.with(context).load(item?.images?.get(0)?.url).into(itemView.iv_playlist)
        }

    }

}