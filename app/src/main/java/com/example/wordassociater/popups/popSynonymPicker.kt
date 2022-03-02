package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.PopFamPickerBinding
import com.example.wordassociater.fams.FamRecycler
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.CommonWord
import com.example.wordassociater.utils.Helper

fun popFamPicker(from: View, word: Word, onFamPicked: (fam: Fam) -> Unit, famList: List<Fam>) {
    val liveList = MutableLiveData<List<Fam>>()
    val b = PopFamPickerBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from, null, null, 100)

    fun interceptedOnFamClicked(fam: Fam) {
        onFamPicked(fam)
        pop.dismiss()
    }

    fun blankFunc() {

    }

    fun blankCommonWord(fam: Fam, type: CommonWord.Type) {

    }

    fun blankUpgrade(fam: Fam) {

    }

    b.famRecycler.initRecycler(FamRecycler.Type.Popup, word, liveList, ::blankFunc, ::interceptedOnFamClicked,::blankUpgrade, ::blankCommonWord)
    liveList.value = famList
}