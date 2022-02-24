package com.example.wordassociater.snippets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.FragmentEditSnippetBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.fire_classes.WordConnection
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.popups.popCharacterSelector
import com.example.wordassociater.popups.popSearchWord
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.SearchHelper

class EditSnippetFragment: Fragment() {
    lateinit var b : FragmentEditSnippetBinding
    lateinit var charAdapter: CharacterAdapter
    private val liveWordList = MutableLiveData<MutableList<Word>>()
    // Character
    private val characterList = MutableLiveData<MutableList<Character>>()
    companion object {
        var oldSnippet = Snippet()
        lateinit var snippet: Snippet
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentEditSnippetBinding.inflate(inflater)
        setWordList()
        setCharacterList()
        setClickListener()
        setContent()
        setRecycler()
        setObserver()
        return b.root
    }

    private fun setWordList() {
        SearchHelper.setWordList(snippet.getWords(), liveWordList)
    }

    private fun setCharacterList() {
        characterList.value = Main.characterList.value?.toMutableList()
        val newList = characterList.value!!.toMutableList()
        for(char in newList) {
            if(snippet.getCharacters().contains(char)) char.selected = true
        }
        characterList.value = newList
    }

    private fun setClickListener() {
        b.saveBtn.setOnClickListener {
            saveSnippet()
            findNavController().navigate(R.id.action_editSnippetFragment_to_snippetFragment)
        }

        b.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_editSnippetFragment_to_snippetFragment)
        }

        b.characterBtn.setOnClickListener {
            popCharacterSelector(b.characterBtn, characterList, ::handleCharacterSelected)
        }

        b.wordIcon.setOnClickListener {
            popSearchWord(b.wordIcon, ::handleWordClick , liveWordList)
        }

        b.associatedWords.setOnClickListener {
            popSearchWord(b.wordIcon, ::handleWordClick , liveWordList)
        }
    }

    private fun handleWordClick(word: Word) {
        word.selected = !word.selected
        if(word.selected) {
            if(!snippet.wordList.contains(word.id)) snippet.wordList.add(word.id)
        }
        else snippet.wordList.remove(word.id)
        setWordList()
    }

    private fun saveSnippet() {
        snippet.content = b.snippetInput.text.toString()
        val charList = mutableListOf<Long>()
        for(c in characterList.value!!) {
            if(c.selected) charList.add(c.id)
        }
        snippet.characterList = charList
        FireSnippets.add(snippet, context)
        characterList.value = mutableListOf()
        WordConnection.handleWordConnections(snippet)
    }

    private fun handleWordConnections() {

    }

    private fun handleWordDeselected() {

    }

    private fun handleCharacterDeselected() {

    }


    private fun setContent() {
        b.snippetInput.setText(snippet.content)
        Log.i("wordPopUp", "editSnippet/setContent, snippet.getwords are : ${snippet.getWords()}")
       b.associatedWords.text =  Helper.setWordsToMultipleLines(snippet.getWords())
    }

    private fun setObserver() {
        liveWordList.observe(context as LifecycleOwner) {
            val selectedWords = mutableListOf<Word>()
            for(w  in it) {
                if(w.selected && !selectedWords.contains(w)) selectedWords.add(w)
            }
            b.associatedWords.text = Helper.setWordsToString(selectedWords)
        }

        characterList.observe(context as LifecycleOwner) {
            val selectedChars = mutableListOf<Character>()
            for(c in it) {
                if(c.selected) selectedChars.add(c)
            }
            charAdapter.submitList(selectedChars)
        }
    }

    private fun setRecycler() {
        charAdapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        b.characterRecycler.adapter = charAdapter
        charAdapter.submitList(snippet.getCharacters())

    }

    private fun handleCharacterSelected(char: Character) {
        char.selected = !char.selected
        val newList = Helper.getResubmitList(char, characterList.value!!)
        characterList.value = newList
    }
}