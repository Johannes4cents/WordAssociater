package com.example.wordassociater.storylines

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarStoryBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.popups.popCharacterSelector
import com.example.wordassociater.popups.popSearchWord
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.ListHelper
import com.example.wordassociater.utils.StoryPart

class StoryBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarStoryBinding.inflate(LayoutInflater.from(context), this, true)
    val selectedWords = MutableLiveData<List<Word>>(mutableListOf())
    val selectedCharacter = MutableLiveData<List<Character>>(mutableListOf())
    val selectedTypes = MutableLiveData<List<StoryPart.Type>>(mutableListOf(StoryPart.Type.Snippet, StoryPart.Type.Event))

    init {
        setClickListener()
        setObserver()
        setCharacterList()
    }

    private fun setCharacterList() {
        val charList = Main.characterList.value!!.toMutableList()
        for(c in charList) {
            c.selected = true
        }
        selectedCharacter.value = charList
    }


    private fun setClickListener() {

        b.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_storyLinesListFragment_to_ViewPagerFragment)
        }
        b.btnCharacter.setOnClickListener {
            popCharacterSelector(b.btnCharacter, selectedCharacter, ::onCharacterSelected)
        }
        b.btnWord.setOnClickListener {
            popSearchWord(b.btnWord, ::handleWordClick , selectedWords)
        }
        b.iconSnippets.setOnClickListener {
            val newList = selectedTypes.value!!.toMutableList()
            if(newList.contains(StoryPart.Type.Snippet)) newList.remove(StoryPart.Type.Snippet)
            else newList.add((StoryPart.Type.Snippet))
            selectedTypes.value = newList
        }

        b.btnProse.setOnClickListener {
            val newList = selectedTypes.value!!.toMutableList()
            if(newList.contains(StoryPart.Type.Prose)) newList.remove(StoryPart.Type.Prose)
            else newList.add((StoryPart.Type.Prose))
            selectedTypes.value = newList
        }
    }

    private fun handleWordClick(word: Word) {
        word.selected = !word.selected
        setWordList()
    }

    private fun onCharacterSelected(char: Character) {
        char.selected = !char.selected
        val newList = Helper.getResubmitList(char, selectedCharacter.value!!)
        selectedCharacter.value = newList
    }

    private fun setWordList() {
        ListHelper.setWordList(selectedWords.value!!, selectedWords, true)
    }

    private fun setObserver() {
        selectedTypes.observe(context as LifecycleOwner) {
            b.iconSnippets.setImageResource(if(it.contains(StoryPart.Type.Snippet)) R.drawable.icon_snippets_selected else R.drawable.icon_snippets_unselected)
            b.btnProse.setImageResource(if(it.contains(StoryPart.Type.Prose)) R.drawable.icon_prose_selected else R.drawable.icon_prose_unselected)
        }
    }



}