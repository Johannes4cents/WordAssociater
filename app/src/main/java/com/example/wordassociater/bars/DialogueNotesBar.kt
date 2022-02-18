package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarDialogueNotesBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.popups.popCharacterSelector
import com.example.wordassociater.utils.Helper

class DialogueNotesBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarDialogueNotesBinding.inflate(LayoutInflater.from(context), this, true)

    companion object {
        val popUpCharacterList = MutableLiveData<List<Character>>()
        val selectedCharacters = mutableListOf<Character>()
    }
    lateinit var navController: NavController

    init {
        setClickListener()
    }

    private fun setClickListener() {
        b.notesButton.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_notesFragment)
        }

        b.btnNewDialogue.setOnClickListener {
            popCharacterSelector(b.btnNewDialogue, navController, popUpCharacterList, ::handleSelectedCharacter)
        }
    }

    private fun handleSelectedCharacter(character: Character) {
        if(character.selected) {
            if(selectedCharacters.count() < 4) selectedCharacters.add(character)
            else Helper.toast("you can only select a maximum of 4 characters", context)

        }
        else selectedCharacters.remove(selectedCharacters)
        val newList = popUpCharacterList.value!!.toMutableList()
        var index = newList.indexOf(character)
        newList.remove(character)
        newList.add(index, character)
        popUpCharacterList.value = newList
    }
}