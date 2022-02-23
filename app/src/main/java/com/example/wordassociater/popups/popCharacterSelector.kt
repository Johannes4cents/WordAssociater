package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.character.CharacterPopUpAdapter
import com.example.wordassociater.databinding.PopupCharacterRecyclerBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.utils.Helper

fun popCharacterSelector(
        from: View,
        characterList: MutableLiveData<MutableList<Character>>,
        characterClickedFunc: (character : Character) -> Unit) {

    val b = PopupCharacterRecyclerBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from, 800,1000)
    val adapter = CharacterPopUpAdapter(characterClickedFunc)
    fun setRecycler() {
        b.characterRecycler.adapter = adapter

        characterList.observe(b.root.context as LifecycleOwner) {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
    }

    fun setClickListener() {
        b.clearAllBtn.setOnClickListener {
            pop.dismiss()
        }
    }

    setRecycler()
    setClickListener()


}