package com.cardona.musicdemo.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.cardona.musicdemo.R
import com.cardona.musicdemo.model.dto.myLists.MyListsResponse
import com.cardona.musicdemo.model.dto.userAuth.UserResponse
import com.cardona.musicdemo.model.networkCalls.webServices.SpotifyWebService
import com.cardona.musicdemo.utils.Constants
import com.cardona.musicdemo.utils.Constants.PLAY_LISTS_ENDPOINT
import com.cardona.musicdemo.view.adapters.MyListsAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_user_info.*

/**
 * A simple [Fragment] subclass.
 */
class UserInfoFragment : DaggerFragment() {

    private lateinit var queue: RequestQueue
    private lateinit var sharedPrefs: SharedPreferences

    private lateinit var myListsAdapter: MyListsAdapter
    private lateinit var rv_playlists: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewLayout = inflater.inflate(R.layout.fragment_user_info, container, false)

        rv_playlists = viewLayout.findViewById(R.id.rv_playLists)

        myListsAdapter = MyListsAdapter(context!!, findNavController())
        val layout= LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        rv_playlists.apply {
            adapter = myListsAdapter
            layoutManager = layout
        }

        sharedPrefs = context?.getSharedPreferences("SPOT_AUTH", 0)!!
        queue = Volley.newRequestQueue(context)

        requestUserInfo(SpotifyWebService(queue))

        return viewLayout
    }

    private fun requestUserInfo(spotifyWebService: SpotifyWebService) {

        val sharedPreferences = context?.getSharedPreferences("SPOT_AUTH", 0)
        val token = sharedPreferences?.getString("token", "")

        Log.d("SpotifyInf", token.toString())
        val map = HashMap<String, String>()
        map["Authorization"] = "Bearer $token"

        spotifyWebService.makeApiCall(
            url = Constants.BASE_AUTH,
            method = Request.Method.GET,
            header = map,
            body = null,
            className = UserResponse::class.java
        ){ user ->

            user?.let { userInfo ->

                tv_username_edit.text = userInfo.displayName
                tv_email_edit.text = userInfo.email
                tv_followers_edit.text = userInfo.followers?.total.toString()
                tv_country_edit.text = userInfo.country
                tv_product_edit.text = userInfo.product

                if(userInfo.images?.isNotEmpty()!!){
                    Glide.with(context!!).load(userInfo.images[0]?.url).
                        into(profile_image)
                }

                requestPlayLists(spotifyWebService, map)

            } ?: run{
                Log.e("SpotifyInf", "No user retrieved")
            }

        }

    }

    private fun requestPlayLists(spotifyWebService: SpotifyWebService, header: HashMap<String,String>) {

        spotifyWebService.makeApiCall(
            url = PLAY_LISTS_ENDPOINT,
            method = Request.Method.GET,
            header = header,
            body = null,
            className = MyListsResponse::class.java
        ){ myListsResponse ->

            myListsAdapter.addPlayLists(myListsResponse?.items)

        }

    }

}
