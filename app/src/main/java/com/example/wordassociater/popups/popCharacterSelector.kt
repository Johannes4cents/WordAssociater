package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterPopUpAdapter
import com.example.wordassociater.databinding.PopupCharacterRecyclerBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.utils.Helper

fun popCharacterSelector(
        from: View,
        characterList: MutableLiveData<List<Character>>,
        characterClickedFunc: (character : Character) -> Unit,
        fromMiddle: Boolean = false,
        showAny: Boolean = false) {

    val b = PopupCharacterRecyclerBinding.inflate(LayoutInflater.from(from.context), null, false)
    var pop = Helper.getPopUp(b.root, from, fromMiddle = fromMiddle)

    val adapter = CharacterPopUpAdapter(characterClickedFunc)
    var allSelected = false

    characterList.observe(b.root.context as LifecycleOwner) {
        if(it.isEmpty()) {
            b.btnSelectALl.setImageResource(R.drawable.storyline_unselected)
        }
        else b.btnSelectALl.setImageResource(R.drawable.storyline_selected)
    }
    fun setRecycler() {
        b.characterRecycler.adapter = adapter

        characterList.observe(b.root.context as LifecycleOwner) {
            var submitList = it.filter { c -> c.id != 22L }
            if(showAny) submitList = submitList + listOf(Character.any)
            adapter.submitList(submitList)
            adapter.notifyDataSetChanged()
        }
    }
    fun selectAll() {
        val newList = characterList.value!!.toMutableList()
        for(c in newList) {
            c.selected = true
        }

        characterList.value = newList
        b.btnSelectALl.setImageResource(R.drawable.storyline_selected)
        allSelected = true
        adapter.notifyDataSetChanged()
    }

    fun deselectAll() {
        val newList = characterList.value!!.toMutableList()
        for(c in newList) {
            c.selected = false
        }

        characterList.value = newList
        b.btnSelectALl.setImageResource(R.drawable.storyline_unselected)
        allSelected = false
        adapter.notifyDataSetChanged()
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


    setRecycler()
    setClickListener()


}