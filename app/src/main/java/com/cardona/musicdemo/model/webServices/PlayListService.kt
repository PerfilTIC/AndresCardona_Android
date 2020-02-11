package com.cardona.musicdemo.model.webServices

import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.cardona.musicdemo.model.dto.playlist.PlayListsResponse
import com.google.gson.Gson

class PlayListService(private val queue: RequestQueue, private val token: String) {

    fun getResponse( url: String, callback: (PlayListsResponse?)->(Unit) ){

        val jsonRequest = object: JsonObjectRequest( url, null,
            Response.Listener { response ->

                val gson = Gson()
                val playLists = gson.fromJson(response.toString(), PlayListsResponse::class.java)

                callback(playLists)
            },
            Response.ErrorListener { response ->
                Log.d("SpotifyInf", response.networkResponse.statusCode.toString())
                callback(null)
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["Authorization"] = "Bearer $token"

                return map
            }
        }

        queue.add(jsonRequest)

    }

}