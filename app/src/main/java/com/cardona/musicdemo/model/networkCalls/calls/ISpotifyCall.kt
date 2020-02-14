package com.cardona.musicdemo.model.networkCalls.calls

import org.json.JSONObject

interface ISpotifyCall {
    fun setMethod( method: Int )
    fun setHeaders( headers: HashMap<String, String> )
    fun setBody( body: JSONObject? )
    fun getResponse( url: String, callback: (JSONObject?)->(Unit) )
}