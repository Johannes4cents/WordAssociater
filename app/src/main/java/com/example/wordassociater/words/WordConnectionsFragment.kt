package com.example.wordassociater.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentWordConnectionsBinding
import com.example.wordassociater.fire_classes.Word

class WordConnectionsFragment: Fragment() {
    lateinit var b: FragmentWordConnectionsBinding
    lateinit var adapter: WordConnectionAdapter
    companion object {
        lateinit var word: Word
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentWordConnectionsBinding.inflate(inflater)
        setClickListener()
        setRecycler()
        return b.root
    }

    private fun setClickListener() {
        b.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_wordConnectionsFragment_to_wordDetailedFragment)
        }
    }

    private fun setRecycler() {
        adapter = WordConnectionAdapter()
        b.connectionsRecycler.adapter = adapter
        adapter.submitList(word.connections)
    }

}