package com.example.wordassociater.wordcat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.FragmentWordCatDetailedBinding
import com.example.wordassociater.fire_classes.WordCat

class WordCatDetailedFragment: Fragment() {
    lateinit var b : FragmentWordCatDetailedBinding
    private val selectedWordCat = MutableLiveData<WordCat>()
    private val selectedWordCatList = MutableLiveData<List<WordCat>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentWordCatDetailedBinding.inflate(inflater)
        b.wordCatRecycler.setupRecycler(WordCatAdapter.Type.BTN, ::onWordCatSelected, selectedWordCatList)
        setObserver()
        return b.root
    }

    private fun onWordCatSelected(wordCat: WordCat) {
        selectedWordCat.value = wordCat
    }

    private fun setObserver() {
        selectedWordCat.observe(viewLifecycleOwner) {

        }
    }
}