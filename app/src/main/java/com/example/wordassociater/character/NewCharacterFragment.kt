package com.example.wordassociater.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wordassociater.databinding.FragmentNewCharacterBinding

class NewCharacterFragment: Fragment() {
    lateinit var b : FragmentNewCharacterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentNewCharacterBinding.inflate(inflater)
        return b.root
    }

    private fun setTopBar() {

    }
}