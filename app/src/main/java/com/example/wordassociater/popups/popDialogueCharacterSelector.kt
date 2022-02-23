package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.bars.AddStuffBar
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.PopupCharacterSelectorBinding
import com.example.wordassociater.dialogue.EditDialogueFragment
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.utils.Helper

fun popCharacterSelector(from: View, navController: NavController, characterList: MutableLiveData<List<Character>>, selectCharFunc : (char: Character) -> Unit) {
    val b = PopupCharacterSelectorBinding.inflate(LayoutInflater.from(from.context), null, false)
    val adapter = CharacterAdapter(CharacterAdapter.Mode.MAIN, selectFunc = selectCharFunc)
    val popUp = Helper.getPopUp(b.root, from, 1000, 900)

    fun setRecycler() {
        b.characterRecycler.adapter = adapter
        characterList.observe(from.context as LifecycleOwner) {
            adapter.submitList(it)
        }
        adapter.submitList(Main.characterList.value)
    }


    fun setBinding() {
        b.goToNewDialogueBtn.setOnClickListener {
            EditDialogueFragment.dialogue.characterList = Character.getIdList(AddStuffBar.selectedCharacters)
            navController.navigate(R.id.action_startFragment_to_editDialogueFragment)
            popUp.dismiss()
        }

    }

    fun setObserver() {
        characterList.observe(from.context as LifecycleOwner) {
            var selectedCharsList = it.filter { c -> c.selected }
            b.selectedCharacterCounter.text = selectedCharsList.count().toString()
        }
    }

    setBinding()
    setRecycler()
    setObserver()

    popUp.setOnDismissListener {
        AddStuffBar.selectedCharacters.clear()
    }



}