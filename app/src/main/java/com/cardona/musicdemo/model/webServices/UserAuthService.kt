package com.cardona.musicdemo.model.webServices

import android.content.SharedPreferences
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.cardona.musicdemo.model.dto.user.UserResponse
import com.google.gson.Gson

class UserAuthService(
    private val queue: RequestQueue,
    private val sharedPreferences: SharedPreferences
) {

    fun getResponse( url: String, callback: (UserResponse?)->(Unit) ){

        val jsonRequest = object: JsonObjectRequest( url, null,
            Response.Listener { response ->

                val gson = Gson()
                val user = gson.fromJson(response.toString(), UserResponse::class.java)

                callback(user)
            },
            Response.ErrorListener { response ->
                Log.d("SpotifyInf", response.networkResponse.statusCode.toString())
                callback(null)
            }
        ){

            override fun getHeaders(): MutableMap<String, String> {
                val token = sharedPreferences.getString("token", "")
                val map = HashMap<String, String>()
                map["Authorization"] = "Bearer $token"

                return map
            }
        }

        queue.add(jsonRequest)

    }

}