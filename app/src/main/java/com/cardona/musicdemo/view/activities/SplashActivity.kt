package com.cardona.musicdemo.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cardona.musicdemo.R
import com.cardona.musicdemo.utils.Constants
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        authenticateSpotify()

    }

    private fun authenticateSpotify() {

        val builder = AuthenticationRequest.Builder(
            Constants.CLIENT_ID,
            AuthenticationResponse.Type.TOKEN,
            Constants.REDIRECT_URI
        )

        builder.setScopes(arrayOf(Constants.SCOPES))
        val request = builder.build()

        val intent = AuthenticationClient.createLoginActivityIntent(this, request)
        startActivityForResult(intent, Constants.REQUEST_CODE_SPOTIFY_LOGIN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            Constants.REQUEST_CODE_SPOTIFY_LOGIN -> {

                val response = AuthenticationClient.getResponse(resultCode, data)

                when(response.type) {

                    AuthenticationResponse.Type.TOKEN -> {
                        //Log.d("SpotifyInf", response.state)

                        this.getSharedPreferences("SPOT_AUTH", 0)?.edit().also {
                            it?.putString("token", response.accessToken)
                            it?.apply()
                        }

                        startActivity(Intent(this, MainActivity::class.java))

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
