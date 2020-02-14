package com.cardona.musicdemo.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import com.cardona.musicdemo.R

/**
 * A simple [Fragment] subclass.
 */
class SongPlayerFragment : Fragment() {

    val args: SongPlayerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewLayout = inflater.inflate(R.layout.fragment_song_player, container, false)

        return viewLayout
    }


}
