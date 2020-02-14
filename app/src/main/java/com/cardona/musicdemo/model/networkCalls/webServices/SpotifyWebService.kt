package com.cardona.musicdemo.model.networkCalls.webServices

import com.android.volley.RequestQueue
import com.cardona.musicdemo.model.networkCalls.calls.ISpotifyCall
import com.cardona.musicdemo.model.networkCalls.calls.SpotifyCall
import com.google.gson.Gson
import org.json.JSONObject

class SpotifyWebService(
    queue: RequestQueue
): ISpotifyWeb {

    private var spotifyCall: ISpotifyCall = SpotifyCall(queue)

    override fun <T> makeApiCall(
        url: String,
        method: Int,
        header: HashMap<String, String>,
        body: JSONObject?,
        className: Class<T>,
        callback: (T?)->(Unit)
    ){

        spotifyCall.setHeaders(header)
        spotifyCall.setBody(body)

        spotifyCall.getResponse(url){ response ->
            val data = Gson().fromJson(response.toString(), className)
            callback(data)
        }

    }

}