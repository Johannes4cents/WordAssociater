package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.PopSynonymPickerBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.synonyms.SynonymRecycler
import com.example.wordassociater.utils.Helper

fun popSynonymPicker(from: View, word: Word, onSynonymPicked: (String) -> Unit, onHeaderClicked: () -> Unit, synonymList: List<String>) {
    val liveList = MutableLiveData<List<String>>()
    val b = PopSynonymPickerBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from, null,null, true)

    fun interceptedOnSynonymClicked(string: String) {
        onSynonymPicked(string)
        pop.dismiss()
    }
    b.synonymRecycler.initRecycler(SynonymRecycler.Type.Popup, word, liveList, onHeaderClicked, ::interceptedOnSynonymClicked)
    liveList.value = synonymList
}