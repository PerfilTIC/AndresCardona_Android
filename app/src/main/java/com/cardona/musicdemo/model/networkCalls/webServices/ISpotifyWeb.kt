package com.cardona.musicdemo.model.networkCalls.webServices

import org.json.JSONObject

interface ISpotifyWeb {

    fun <T> makeApiCall(
        url: String,
        method: Int,
        header: HashMap<String, String>,
        body: JSONObject?,
        className: Class<T>,
        callback: (T?)->(Unit)
    )

}