package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import com.example.wordassociater.Main
import com.example.wordassociater.databinding.PopWordCatBinding
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.wordcat.WordCatAdapter

fun popSingleSelectWordCat(from: View, onCatSelected: (wordCat: WordCat) -> Unit) {
    val b = PopWordCatBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from, 750,600)

    fun interceptOnCatSelected(wordCat: WordCat) {
        onCatSelected(wordCat)
        pop.dismiss()
    }

    val adapter = WordCatAdapter(WordCatAdapter.Type.SINGLEPICK, ::interceptOnCatSelected)
    b.wordCatRecycler.adapter = adapter
    adapter.submitList(Main.wordCatsList.value!!)

}