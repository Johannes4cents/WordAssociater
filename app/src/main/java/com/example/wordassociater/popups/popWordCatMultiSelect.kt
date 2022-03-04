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

    b.btnNewCat.setOnClickListener {
        popNewWordCat(b.btnNewCat)
    }

    liveList.observe(from.context as LifecycleOwner) {
        adapter.submitList(it)
        adapter.notifyDataSetChanged()
    }

}


fun popWordCatAllOptions(from: View, liveList: MutableLiveData<List<WordCat>>, onWordCatClicked: (wordCat: WordCat) -> Unit, onDeleteClicked: ((wordCat: WordCat) -> Unit)? = null) {
    val b = PopWordCatMultiSelectBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from, null,null)

    val adapter = WordCatAdapter(WordCatAdapter.Type.ALLOPTIONS, onWordCatClicked, null, onDeleteClicked)
    b.wordCatRecycler.adapter = adapter

    b.btnNewCat.setOnClickListener {
        popNewWordCat(from)
    }

    liveList.observe(from.context as LifecycleOwner) {
        adapter.submitList(it.sortedBy { wc -> wc.id }.reversed())
        adapter.notifyDataSetChanged()
    }

}
