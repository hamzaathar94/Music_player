package com.example.music_player.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.music_player.R
import com.example.music_player.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var binding:FragmentSplashBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSplashBinding.inflate(LayoutInflater.from(requireContext()),container,false)


        return binding?.root
    }


}