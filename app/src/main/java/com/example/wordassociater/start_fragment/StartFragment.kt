package com.example.wordassociater.start_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentStartBinding

class StartFragment: Fragment() {
    lateinit var b : FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentStartBinding.inflate(inflater)
        b.readWriteBar.navController = findNavController()
        b.characterBar.navController = findNavController()
        setClickListener()
        return b.root
    }

    private fun setClickListener() {
        b.notesButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_notesFragment)
        }
    }


}