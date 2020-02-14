package com.cardona.musicdemo.model.repositories

interface ISpotifyRepository {

    fun <T> getFromApi(url: String, responseClassName: Class<T>, header: HashMap<String, String>)

}