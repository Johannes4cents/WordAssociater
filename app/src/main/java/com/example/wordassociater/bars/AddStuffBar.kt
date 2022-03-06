package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarAddStuffBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.utils.Helper

class AddStuffBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarAddStuffBinding.inflate(LayoutInflater.from(context), this, true)
    lateinit var navController: NavController

    companion object {
        val popUpCharacterList = MutableLiveData<List<Character>>(mutableListOf())
        val selectedCharacters = mutableListOf<Character>()
        val snippetInputOpen = MutableLiveData(false)
        var newWordInputOpen = MutableLiveData(false)
    }

    init {
        setClickListener()
        setObserver()
    }

    private fun getCharacterList() {
        val notSelectedList = mutableListOf<Character>()
        val noRandomPersonList = Main.characterList.value!!.toMutableList().filter { c -> c.id != 16L }
        for(c in noRandomPersonList) {
            c.selected = false
            notSelectedList.add(c)
        }
        val randomPerson = Main.characterList.value!!.find { c -> c.id == 16L }!!.copy()
        popUpCharacterList.value = listOf(randomPerson) + notSelectedList

    }

    private fun setClickListener() {

        b.btnNewSnippet.setOnClickListener {
            if(snippetInputOpen.value!!) b.newSnippetBar.saveSnippet()
            else snippetInputOpen.value = true
        }

        b.btnNewWord.setOnClickListener {
            newWordInputOpen.value = !newWordInputOpen.value!!
        }

    }

    private fun setObserver() {
        snippetInputOpen.observe(context as LifecycleOwner) {
            if(it == true) {
                b.newSnippetBar.visibility = View.VISIBLE
                b.btnNewSnippet.setImageResource(R.drawable.btn_save)
            }
            else {
                b.newSnippetBar.visibility = View.GONE
                b.btnNewSnippet.setImageResource(R.drawable.btn_new_snippet)
            }
        }
    }

    private fun handleSelectedCharacter(character: Character) {
        character.selected = !character.selected
        if(character.selected) {
            if(selectedCharacters.count() < 4) selectedCharacters.add(character)
            else {
                character.selected = false
                Helper.toast("4 Characters max", context)
            }
        }
        else {
            selectedCharacters.remove(character)
        }

        val newList = Helper.getResubmitList(character, popUpCharacterList.value!!)
        popUpCharacterList.value = newList

    }
}