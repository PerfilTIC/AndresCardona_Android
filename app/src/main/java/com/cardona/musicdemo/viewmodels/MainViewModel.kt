package com.cardona.musicdemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cardona.musicdemo.model.persistence.entities.PlayListEntity
import com.cardona.musicdemo.model.persistence.entities.UserEntity
import com.cardona.musicdemo.model.repositories.SpotifyRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val spotifyRepository: SpotifyRepository): ViewModel() {

    var users: LiveData<UserEntity> = MutableLiveData()
    var playLists: LiveData<List<PlayListEntity>> = MutableLiveData()

    var mediatorLiveData: MediatorLiveData<List<Any>> = MediatorLiveData()

    fun <T> getFromRepo(url: String, responseClassName: Class<T>, header: HashMap<String, String>){
        spotifyRepository.getFromApi(url, responseClassName, header)
    }

    fun getUser(){

        users = spotifyRepository.getUserInfoFromRoom()

        mediatorLiveData.removeSource(users)

        mediatorLiveData.addSource(users){ data ->
            mediatorLiveData.value = listOf(data)
        }
    }

    fun getPlayList(){

        playLists = spotifyRepository.getPlayListFromRoom()

        mediatorLiveData.removeSource(playLists)

        mediatorLiveData.addSource(playLists){ data ->
            mediatorLiveData.value = data
        }

    }

}