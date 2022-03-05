package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterRecycler
import com.example.wordassociater.databinding.PopupCharacterRecyclerBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.LiveClass

fun popCharacterSelector(
        from: View,
        characterList: MutableLiveData<List<Character>>,
        characterClickedFunc: (character : LiveClass) -> Unit,
        fromMiddle: Boolean = false,
        showAny: Boolean = false) {

    val b = PopupCharacterRecyclerBinding.inflate(LayoutInflater.from(from.context), null, false)
    var pop = Helper.getPopUp(b.root, from, fromMiddle = fromMiddle)

    b.characterRecycler.initRecycler(CharacterRecycler.Mode.Popup, characterList, characterClickedFunc)
    var allSelected = false

    characterList.observe(b.root.context as LifecycleOwner) {
        if(it.isEmpty()) {
            b.btnSelectALl.setImageResource(R.drawable.storyline_unselected)
        }
        else b.btnSelectALl.setImageResource(R.drawable.storyline_selected)
    }

    fun selectAll() {
        val newList = characterList.value!!.toMutableList()
        for(c in newList) {
            c.selected = true
        }

        characterList.value = newList
        b.btnSelectALl.setImageResource(R.drawable.storyline_selected)
        allSelected = true
    }

    fun deselectAll() {
        val newList = characterList.value!!.toMutableList()
        for(c in newList) {
            c.selected = false
        }

        characterList.value = newList
        b.btnSelectALl.setImageResource(R.drawable.storyline_unselected)
        allSelected = false
    }

    fun setClickListener() {
        b.btnBack.setOnClickListener {
            pop.dismiss()
        }

        b.btnSelectALl.setOnClickListener {
            if(allSelected) {
                deselectAll()
            }
            else {
                selectAll()
            }
        }
    }

    setClickListener()


}