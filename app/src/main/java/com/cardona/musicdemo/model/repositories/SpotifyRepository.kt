package com.cardona.musicdemo.model.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.android.volley.Request
import com.cardona.musicdemo.model.dto.playList.PlayListResponse
import com.cardona.musicdemo.model.dto.userAuth.UserResponse
import com.cardona.musicdemo.model.networkCalls.webServices.SpotifyWebService
import com.cardona.musicdemo.model.persistence.databases.SpotifyPlayDatabase
import com.cardona.musicdemo.model.persistence.entities.PlayListEntity
import com.cardona.musicdemo.model.persistence.entities.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import javax.inject.Inject

class SpotifyRepository @Inject constructor(
    private val spotifyWebService: SpotifyWebService,
    private val spotifyPlayDatabase: SpotifyPlayDatabase
): ISpotifyRepository {

    override fun <T> getFromApi(url: String, responseClassName: Class<T>, header: HashMap<String, String>) {

        Log.d("myquery", "ready")

        spotifyWebService.makeApiCall(
            url = url,
            method = Request.Method.GET,
            header = header,
            body = null,
            className = responseClassName
        ){ response ->

            Log.d("myquery", "finished")

            when(response){

                is UserResponse -> {

                    Log.d("myquery", "finished type 1")

                    val userInfo = response as UserResponse

                    var photo : String = "noPhoto"
                    if(userInfo.images?.isNotEmpty()!!){
                        photo = userInfo.images[0]?.url!!
                    }

                    CoroutineScope(IO).launch {
                        spotifyPlayDatabase.usersDao().insertUsers(
                            UserEntity(
                                username = userInfo.displayName!!,
                                email = userInfo.email,
                                followers = userInfo.followers?.total.toString(),
                                country = userInfo.country,
                                product = userInfo.product,
                                photo = photo

                            )
                        )
                    }

                }

                is PlayListResponse -> {

                    Log.d("myquery", "finished type 2")

                    val playList = response as PlayListResponse

                    val names = mutableListOf<String?>()
                    val artists = mutableListOf<String?>()
                    val durations = mutableListOf<String?>()
                    val popularities = mutableListOf<String?>()
                    val uris = mutableListOf<String?>()

                    playList.tracks?.items?.forEach { item ->

                        val track = item?.track

                        names.add(track?.name)
                        artists.add(track?.artists?.get(0)?.name)
                        durations.add(track?.durationMs.toString())
                        popularities.add(track?.popularity.toString())
                        uris.add(track?.uri)

                    }

                    CoroutineScope(IO).launch {
                        spotifyPlayDatabase.listDao().insertPlayList(
                            PlayListEntity(
                                playListId = playList.id.toString(),
                                playListName = playList.name.toString(),
                                followers = playList.followers.toString(),
                                songNames = names,
                                artists = artists,
                                durations = durations,
                                popularities = popularities,
                                songUris = uris,
                                imageUrl = playList.images?.get(0)?.url
                            )
                        )
                    }

                }

            }

        }

    }

    fun getUserInfoFromRoom(): LiveData<UserEntity>{
        return spotifyPlayDatabase.usersDao().getUsers()
    }

    fun getPlayListFromRoom(): LiveData<List<PlayListEntity>>{
        return spotifyPlayDatabase.listDao().getPlayList()
    }

}