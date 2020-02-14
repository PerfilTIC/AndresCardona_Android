package com.cardona.musicdemo.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.bumptech.glide.Glide
import com.cardona.musicdemo.R
import com.cardona.musicdemo.model.dto.myLists.MyListsResponse
import com.cardona.musicdemo.model.dto.playList.PlayListResponse
import com.cardona.musicdemo.model.dto.userAuth.UserResponse
import com.cardona.musicdemo.model.networkCalls.webServices.SpotifyWebService
import com.cardona.musicdemo.model.persistence.entities.PlayListEntity
import com.cardona.musicdemo.model.persistence.entities.UserEntity
import com.cardona.musicdemo.utils.Constants
import com.cardona.musicdemo.utils.Constants.PLAY_LISTS_ENDPOINT
import com.cardona.musicdemo.view.adapters.MyListsAdapter
import com.cardona.musicdemo.viewmodels.MainViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_user_info.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class UserInfoFragment : DaggerFragment() {

    @Inject
    protected lateinit var spotifyWebService: SpotifyWebService

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var vm: MainViewModel

    private lateinit var sharedPrefs: SharedPreferences

    private lateinit var myListsAdapter: MyListsAdapter
    private lateinit var rv_playlists: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewLayout = inflater.inflate(R.layout.fragment_user_info, container, false)
        vm = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        rv_playlists = viewLayout.findViewById(R.id.rv_playLists)

        myListsAdapter = MyListsAdapter(context!!, findNavController())
        val layout= LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        rv_playlists.apply {
            adapter = myListsAdapter
            layoutManager = layout
        }

        sharedPrefs = context?.getSharedPreferences("SPOT_AUTH", 0)!!
        requestUserInfo()

        vm.mediatorLiveData.observe(viewLifecycleOwner, Observer { data ->

            Log.d("myquery", "received")

            if(data.isNotEmpty()){
                when(data[0]){

                    is UserEntity -> {

                        Log.d("myquery", "received type 1")

                        val userInfo = data[0] as UserEntity

                        tv_username_edit.text = userInfo.username
                        tv_email_edit.text = userInfo.email
                        tv_followers_edit.text = userInfo.followers
                        tv_country_edit.text = userInfo.country
                        tv_product_edit.text = userInfo.product

                        if(!userInfo.photo.equals("noPhoto")) {
                            Glide.with(context!!).load(userInfo.photo).into(profile_image)
                        }

                        requestPlayLists()

                    }

                    is PlayListEntity -> {

                        Log.d("myquery", "received type 2")

                        val myListsResponse = data as List<PlayListEntity>
                        if(myListsResponse.isNotEmpty()){
                            //myListsAdapter.addPlayLists(myListsResponse)
                        }


                    }

                }
            }

        })

        vm.getUser()
        vm.getPlayList()

        return viewLayout
    }

    private fun requestUserInfo() {

        val sharedPreferences = context?.getSharedPreferences("SPOT_AUTH", 0)
        val token = sharedPreferences?.getString("token", "")

        Log.d("SpotifyInf", token.toString())
        val map = HashMap<String, String>()
        map["Authorization"] = "Bearer $token"

        vm.getFromRepo(Constants.BASE_AUTH, UserResponse::class.java, map)

    }

    private fun requestPlayLists() {

        val sharedPreferences = context?.getSharedPreferences("SPOT_AUTH", 0)
        val token = sharedPreferences?.getString("token", "")
        Log.d("SpotifyInf", token.toString())
        val map = HashMap<String, String>()
        map["Authorization"] = "Bearer $token"

        spotifyWebService.makeApiCall(
            url = PLAY_LISTS_ENDPOINT,
            method = Request.Method.GET,
            header = map,
            body = null,
            className = MyListsResponse::class.java
        ){ myListsResponse ->

            myListsAdapter.addPlayLists(myListsResponse?.items)

        }

        //vm.getFromRepo(PLAY_LISTS_ENDPOINT, PlayListResponse::class.java, map)

    }

}
