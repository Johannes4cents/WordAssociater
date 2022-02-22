package com.example.wordassociater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.databinding.FragmentStartBinding

class StartFragment: Fragment() {
    lateinit var b : FragmentStartBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentStartBinding.inflate(inflater)
        b.contentBar.navController = findNavController()
        b.addStuffBar.navController = findNavController()
        return b.root
    }


}