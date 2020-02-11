package com.cardona.musicdemo.utils

object Constants {

    const val CLIENT_ID = "14d36268cd7b45c38f27d5dcd55cb50b"
    const val REDIRECT_URI = "com.cardona.musicdemo://callback"

    //Permissions to request from the user
    const val SCOPES = "user-read-recently-played,user-library-modify,user-read-email,user-read-private"

    const val REQUEST_CODE_SPOTIFY_LOGIN = 3221
    const val REQUEST_CODE_SPOTIFY_PLAY_LISTS = 3222

    const val BASE_URL = "https://api.spotify.com/v1/me"
    const val PLAY_LISTS_ENDPOINT = "$BASE_URL/playlists"

}