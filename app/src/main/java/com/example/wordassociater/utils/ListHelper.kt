package com.example.wordassociater.utils

import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.StoryPart
import com.example.wordassociater.fire_classes.Word

object ListHelper {
    fun setWordList(selectedWordList: List<Word>, liveWordList: MutableLiveData<List<Word>>, plusAny: Boolean = false )  {
        val wordList = Main.wordsList.value!!.toMutableList()
        for(w in wordList) {
            w.selected = selectedWordList.contains(w)
        }

        val newList = selectedWordList + wordList.sortedBy { w -> w.text }.sortedBy { w -> w.selected }.toMutableList()
        var anyList = newList.toMutableList()
        if(plusAny) {

            val any = anyList.find { w -> w.text == "Any" }
            anyList.remove(any!!)

            anyList = (mutableListOf(any) + anyList).toMutableList()

        }

        liveWordList.value = if(!plusAny) newList.toMutableList() else anyList
    }


    fun handleSelectAndSetFullCharacterList(storyPart: StoryPart, liveList: MutableLiveData<List<Character>> ) {
        val notSelectedList = mutableListOf<Character>()
        for(c in Main.characterList.value!!) {
            c.selected = storyPart.characterList.contains(c.id)
            notSelectedList.add(c)
        }
        liveList.value = notSelectedList
    }

    fun checkIfCharacterSelected(list: List<Character>): Boolean {
        var selected = false
        for(c in list) {
            if(c.selected) selected = true; break
        }
        return selected
    }



}