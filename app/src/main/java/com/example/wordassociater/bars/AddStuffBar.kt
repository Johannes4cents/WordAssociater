package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.wordassociater.Frags
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarAddStuffBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.popups.popCharacterSelector
import com.example.wordassociater.strains.StrainEditFragment
import com.example.wordassociater.utils.Helper

class AddStuffBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarAddStuffBinding.inflate(LayoutInflater.from(context), this, true)
    lateinit var navController: NavController

    companion object {
        val popUpCharacterList = MutableLiveData<List<Character>>()
        val selectedCharacters = mutableListOf<Character>()
        val snippetInputOpen = MutableLiveData(false)
        var newWordInputOpen = MutableLiveData(false)
    }

    init {
        setClickListener()
        setObserver()
    }

    private fun setClickListener() {

        b.btnNewDialogue.setOnClickListener {

            popCharacterSelector(b.btnNewDialogue, navController, popUpCharacterList, ::handleSelectedCharacter)
        }

        b.btnNewSnippet.setOnClickListener {
            snippetInputOpen.value = !snippetInputOpen.value!!
        }

        b.btnNewStrain.setOnClickListener {
            StrainEditFragment.comingFrom = Frags.START
            findNavController().navigate(R.id.action_startFragment_to_writeFragment)
        }

        b.btnNewWord.setOnClickListener {
            newWordInputOpen.value = !newWordInputOpen.value!!
        }

    }

    private fun setObserver() {
        snippetInputOpen.observe(context as LifecycleOwner) {
            if(it == true) {
                b.newSnippetBar.visibility = View.VISIBLE
            }
            else {
                b.newSnippetBar.visibility = View.GONE
            }
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

        val randomPerson = newList.find { c -> c.id == 16L }
        newList.remove(randomPerson)


        popUpCharacterList.value = listOf(randomPerson!!) + newList
    }
}