package com.cardona.musicdemo.utils

object Constants {

    const val CLIENT_ID = "14d36268cd7b45c38f27d5dcd55cb50b"
    const val REDIRECT_URI = "com.cardona.musicdemo://callback"

    //Permissions to request from the user
    private const val USER_SCOPES = "user-read-recently-played,user-library-modify,user-read-email,user-read-private"
    private const val PLAY_LIST_SCOPES = "playlist-read-collaborative,playlist-modify-public,playlist-read-private,playlist-modify-private"

    const val SCOPES = "$USER_SCOPES,$PLAY_LIST_SCOPES"

    const val REQUEST_CODE_SPOTIFY_LOGIN = 3221
    const val REQUEST_CODE_SPOTIFY_PLAY_LISTS = 3222

    const val BASE = "https://api.spotify.com/v1"
    const val BASE_AUTH = "$BASE/me"
    const val PLAY_LISTS_ENDPOINT = "$BASE_AUTH/playlists"
    const val PLAY_LISTS_SONGS_ENDPOINT = "$BASE/playlists"

}