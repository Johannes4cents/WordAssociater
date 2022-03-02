package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.PopSynonymPickerBinding
import com.example.wordassociater.fams.FamRecycler
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.Helper

fun popFamPicker(from: View, word: Word, onFamPicked: (fam: Fam) -> Unit, famList: List<Fam>) {
    val liveList = MutableLiveData<List<Fam>>()
    val b = PopSynonymPickerBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from, null, null, 100)

    fun interceptedOnSynonymClicked(fam: Fam) {
        onFamPicked(fam)
        pop.dismiss()
    }

    fun blankFunc() {

    }

    b.famRecycler.initRecycler(FamRecycler.Type.Popup, word, liveList, ::blankFunc, ::interceptedOnSynonymClicked)
    liveList.value = famList
}