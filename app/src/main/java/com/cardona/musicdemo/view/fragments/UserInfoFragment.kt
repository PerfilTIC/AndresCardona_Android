package com.cardona.musicdemo.view.fragments


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.cardona.musicdemo.R
import com.cardona.musicdemo.model.webServices.PlayListService
import com.cardona.musicdemo.model.webServices.UserAuthService
import com.cardona.musicdemo.utils.Constants
import com.cardona.musicdemo.utils.Constants.PLAY_LISTS_ENDPOINT
import com.cardona.musicdemo.utils.Constants.REQUEST_CODE_SPOTIFY_LOGIN
import com.cardona.musicdemo.view.adapters.PlayListAdapter
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.playlist_item.view.*

/**
 * A simple [Fragment] subclass.
 */
class UserInfoFragment : Fragment() {

    private lateinit var queue: RequestQueue
    private lateinit var sharedPrefs: SharedPreferences

    private lateinit var playListAdapter: PlayListAdapter
    private lateinit var rv_playlists: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewLayout = inflater.inflate(R.layout.fragment_user_info, container, false)

        rv_playlists = viewLayout.findViewById(R.id.rv_playLists)

        playListAdapter = PlayListAdapter(context!!)
        val layout= LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        rv_playlists.apply {
            adapter = playListAdapter
            layoutManager = layout
        }

        sharedPrefs = context?.getSharedPreferences("SPOT_AUTH", 0)!!
        queue = Volley.newRequestQueue(context)

        authenticateSpotify()

        return viewLayout
    }

    private fun authenticateSpotify() {

        val builder = AuthenticationRequest.Builder(
            Constants.CLIENT_ID,
            AuthenticationResponse.Type.TOKEN,
            Constants.REDIRECT_URI
        )

        builder.setScopes(arrayOf(Constants.SCOPES))
        val request = builder.build()

        val intent = AuthenticationClient.createLoginActivityIntent(activity, request)
        startActivityForResult(intent, REQUEST_CODE_SPOTIFY_LOGIN)

    }

    private fun requestPlayLists(token: String) {
        val playListService = PlayListService(queue, token)

        playListService.getResponse(PLAY_LISTS_ENDPOINT){ playListsResponse ->

            Log.d("myItems", playListsResponse?.items?.get(0)?.toString())
            playListAdapter.addPlayLists(playListsResponse?.items)

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            REQUEST_CODE_SPOTIFY_LOGIN -> {

                val response = AuthenticationClient.getResponse(resultCode, data)

                when(response.type) {

                    AuthenticationResponse.Type.TOKEN -> {
                        //Log.d("SpotifyInf", response.state)

                        context?.getSharedPreferences("SPOT_AUTH", 0)?.edit().also {
                            it?.putString("token", response.accessToken)
                            it?.apply()
                        }

                        //ViewModel
                        UserAuthService(
                            queue,
                            sharedPrefs
                        ).getResponse(Constants.BASE_URL){ user ->

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

                                requestPlayLists(response.accessToken)

                            } ?: run{
                                Log.e("SpotifyInf", "No user retrieved")
                            }

                        }

                    }
                    AuthenticationResponse.Type.ERROR -> {
                        Log.d("SpotifyInf", response.error)
                    }

                    else -> {
                        Log.d("SpotifyInf", response.type.name)
                    }
                }

            }

        }

    }


}
