package com.cardona.musicdemo.model.networkCalls.calls

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class SpotifyCall(
    private val queue: RequestQueue
) : ISpotifyCall {

    private var headers = HashMap<String, String>()
    private var bodyPars : JSONObject? = null
    private var method: Int = Request.Method.GET

    override fun setMethod(method: Int) {
        this.method = method
    }

    override fun setHeaders(headers: HashMap<String, String>) {
        this.headers = headers
    }

    override fun setBody(body: JSONObject?) {
        this.bodyPars = body
    }

    override fun getResponse(url: String, callback: (JSONObject?) -> Unit) {

        val jsonRequest = object: JsonObjectRequest( method, url, null,
            Response.Listener { response ->
                Log.d("SpotifyInf", "$headers")
                callback(response)
            },
            Response.ErrorListener { response ->
                Log.d("SpotifyInf", response.networkResponse.statusCode.toString())
                callback(null)
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["Authorization"] = this@SpotifyCall.headers["Authorization"].toString()
                return map
            }

            override fun getBody(): ByteArray {
                return bodyPars.toString().toByteArray()
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }

        }

        queue.add(jsonRequest)

    }

}