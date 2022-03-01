package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.databinding.PopWordCatMultiSelectBinding
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.wordcat.WordCatAdapter

fun popWordCatMultiSelect(from: View, liveList: MutableLiveData<List<WordCat>>, onWordCatClicked: (wordCat: WordCat) -> Unit) {
    val b = PopWordCatMultiSelectBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from, null,null)

    val adapter = WordCatAdapter(WordCatAdapter.Type.List, onWordCatClicked)
    b.wordCatRecycler.adapter = adapter
    adapter.submitList(Main.wordCatsList.value!!)

    liveList.observe(from.context as LifecycleOwner) {
        adapter.submitList(it)
        adapter.notifyDataSetChanged()
    }

}

fun popWordCatAllOptions(from: View, liveList: MutableLiveData<List<WordCat>>, onWordCatClicked: (wordCat: WordCat) -> Unit) {
    val b = PopWordCatMultiSelectBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from, null,null)

    val adapter = WordCatAdapter(WordCatAdapter.Type.ALLOPTIONS, onWordCatClicked)
    b.wordCatRecycler.adapter = adapter
    adapter.submitList(Main.wordCatsList.value!!)

    liveList.observe(from.context as LifecycleOwner) {
        adapter.submitList(it)
        adapter.notifyDataSetChanged()
    }

}
