package com.cardona.musicdemo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.cardona.musicdemo.model.repositories.SpotifyRepository
import com.cardona.musicdemo.model.networkCalls.webServices.SpotifyWebService
import com.cardona.musicdemo.model.persistence.databases.SpotifyPlayDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule{

    @Provides
    fun provideContext(app: Application): Context {
        return app.baseContext
    }

    @Singleton
    @Provides
    fun providesQueue(context: Context): RequestQueue{
        return Volley.newRequestQueue(context)
    }

    @Singleton
    @Provides
    fun providesSpotifyWebService(queue: RequestQueue): SpotifyWebService {
        return SpotifyWebService(queue)
    }

    @Singleton
    @Provides
    fun providesMainRepository(
        spotifyWebService: SpotifyWebService,
        spotifyPlayDatabase: SpotifyPlayDatabase): SpotifyRepository
    {
        return SpotifyRepository(spotifyWebService, spotifyPlayDatabase)
    }

    @Singleton
    @Provides
    fun providesDatabase(context: Context): SpotifyPlayDatabase{
        return Room.databaseBuilder(context,
            SpotifyPlayDatabase::class.java, "spotDB").build()
    }

}