package com.example.wordassociater.popups

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.bars.AddStuffBar
import com.example.wordassociater.character.CharacterSelectDialogueAdapter
import com.example.wordassociater.databinding.PopupCharacterSelectorBinding
import com.example.wordassociater.dialogue.EditDialogueFragment
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.utils.Helper


fun popDialogueCharacterSelector(from: View, navController: NavController, characterList: MutableLiveData<List<Character>>, selectCharFunc : (char: Character) -> Unit) {
    val b = PopupCharacterSelectorBinding.inflate(LayoutInflater.from(from.context), null, false)
    val popUpAdapter = CharacterSelectDialogueAdapter(selectCharFunc)
    val popUp = Helper.getPopUp(b.root, from, 1000, 900)

    fun setRecycler() {
        for(c in Main.characterList.value!!) {
            if(c.selected) Log.i("characterListProb", "${c.name} is selected")
        }
        b.characterRecycler.adapter = popUpAdapter

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
            popUpAdapter.submitList(it)
            popUpAdapter.notifyDataSetChanged()
        }

    }

    setBinding()
    setRecycler()
    setObserver()

    popUp.setOnDismissListener {
        AddStuffBar.selectedCharacters.clear()
        AddStuffBar.popUpCharacterList.value = mutableListOf()
    }




}