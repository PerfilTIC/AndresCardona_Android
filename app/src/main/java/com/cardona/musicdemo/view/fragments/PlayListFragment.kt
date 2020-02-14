package com.cardona.musicdemo.view.fragments


import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.cardona.musicdemo.R
import com.cardona.musicdemo.model.dto.playList.PlayListResponse
import com.cardona.musicdemo.model.networkCalls.webServices.SpotifyWebService
import com.cardona.musicdemo.utils.Constants.CLIENT_ID
import com.cardona.musicdemo.utils.Constants.PLAY_LISTS_SONGS_ENDPOINT
import com.cardona.musicdemo.utils.Constants.REDIRECT_URI
import com.cardona.musicdemo.view.adapters.PlayListAdapter
import com.cardona.musicdemo.viewmodels.MainViewModel
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_play_list.*
import org.json.JSONObject
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class PlayListFragment : DaggerFragment() {

    @Inject
    protected lateinit var spotifyWebService: SpotifyWebService

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var vm: MainViewModel

    private lateinit var playAdapter : PlayListAdapter
    private lateinit var recycler: RecyclerView

    private val args: PlayListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewLayout = inflater.inflate(R.layout.fragment_play_list, container, false)
        vm = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        setupRecyclerView(viewLayout)
        val sharedPrefs = requestPlayList(args.plId)
        setupRecyclerSwipe(sharedPrefs)

        //playSongRemotely()


        return viewLayout
    }

    private fun setupRecyclerSwipe(sharedPrefs: SharedPreferences) {
        val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.LEFT,
            ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                Toast.makeText(context, "mover", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //Toast.makeText(context, "hola", Toast.LENGTH_SHORT).show()
                //playAdapter.deleteItem(viewHolder.adapterPosition)

                val token = sharedPrefs.getString("token", "")
                val map = HashMap<String, String>()
                map["Authorization"] = "Bearer $token"
                map["Content-Type"] = "application/json"

                val uri = playAdapter.getItem(viewHolder.adapterPosition)?.track?.uri

                val item = JSONObject()
                item.put("uri", uri)

                val finalJson = JSONObject()
                finalJson.put("tracks", item)

                spotifyWebService.makeApiCall(
                    url = "$PLAY_LISTS_SONGS_ENDPOINT/${args.plId}",
                    method = Request.Method.DELETE,
                    header = map,
                    body = finalJson,
                    className = PlayListResponse::class.java
                ) {
                    val m = playAdapter.getItem(viewHolder.adapterPosition)?.track?.availableMarkets
                    Log.d("SpotifyInf", "$m")
                }

            }

        })

        touchHelper.attachToRecyclerView(recycler)
    }

    private fun requestPlayList(plId: String): SharedPreferences {

        val sharedPrefs = context?.getSharedPreferences("SPOT_AUTH", 0)!!

        sharedPrefs.getString("token", "")?.let { token ->

            val map = HashMap<String, String>()
            map["Authorization"] = "Bearer $token"

            spotifyWebService.makeApiCall(
                url = "$PLAY_LISTS_SONGS_ENDPOINT/${plId}",
                method = Request.Method.GET,
                header = map,
                body = null,
                className = PlayListResponse::class.java
            ) { playListResponse ->

                playAdapter.run { addPlayLists(playListResponse?.tracks?.items?.toMutableList()) }
                tv_playlist2.text = playListResponse?.name
                tv_plfollowers.text = playListResponse?.followers?.total.toString()+" followers"
                Glide.with(context!!).load(playListResponse?.images?.get(0)?.url).into(iv_playlist2)

            }
        }
        return sharedPrefs
    }

    private fun setupRecyclerView(viewLayout: View) {
        recycler = viewLayout.findViewById(R.id.rv_playlist)

        playAdapter = PlayListAdapter(context!!)
        val linearLayout = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        recycler.apply {
            adapter = playAdapter
            layoutManager = linearLayout
        }
    }

}
