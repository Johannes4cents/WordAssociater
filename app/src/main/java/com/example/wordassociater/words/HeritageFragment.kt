package com.example.wordassociater.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wordassociater.databinding.FragmentHeritageBinding
import com.example.wordassociater.fire_classes.Word

class HeritageFragment: Fragment() {
    lateinit var b : FragmentHeritageBinding

    companion object {
        lateinit var word: Word
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentHeritageBinding.inflate(inflater)

        return b.root
    }

    private fun setClickListener() {

    }
}