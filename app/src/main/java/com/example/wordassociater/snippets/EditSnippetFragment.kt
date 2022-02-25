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
import com.example.wordassociater.firestore.FireChars
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.firestore.FireWords
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

        setLiveList()
        setClickListener()
        setContent()
        setRecycler()
        setObserver()
        setWordIcon()
        return b.root
    }

    private fun setLiveList() {
        val notSelectedList = mutableListOf<Character>()
        for(c in Main.characterList.value!!) {
            c.selected = snippet.characterList.contains(c.id)
            notSelectedList.add(c)
        }
        characterList.value = notSelectedList
    }

    private fun setWordList() {
        SearchHelper.setWordList(snippet.getWords(), liveWordList)
    }

    private fun setClickListener() {
        b.saveBtn.setOnClickListener {
            saveSnippet()
            findNavController().navigate(R.id.action_editSnippetFragment_to_snippetFragment)
        }

        b.backBtn.setOnClickListener {
            snippet.wordList = oldSnippet.wordList
            snippet.characterList = oldSnippet.characterList
            findNavController().navigate(R.id.action_editSnippetFragment_to_snippetFragment)
        }

        b.characterBtn.setOnClickListener {
            popCharacterSelector(b.characterBtn, characterList, ::handleCharacterSelected)
        }

        b.wordIcon.setOnClickListener {
            setWordList()
            popSearchWord(b.wordIcon, ::handleWordClick , liveWordList)
        }

        b.associatedWords.setOnClickListener {
            setWordList()
            popSearchWord(b.wordIcon, ::handleWordClick , liveWordList)
        }
    }

    private fun handleWordDeselected() {
        for(wordId in oldSnippet.wordList) {
            if(!snippet.wordList.contains(wordId)) {
                val word = Main.getWord(wordId)!!
                WordConnection.disconnect(word, snippet.id)
                Log.i("deselectTest", "word.strainsList before = ${word.strainsList}")
                word.snippetsList.remove(snippet.id)
                word.decreaseWordUsed()
                FireWords.update(word.id, "snippetsList", word.snippetsList)
                Log.i("deselectTest", "word.strainsList after = ${word.strainsList}")
            }
        }
    }



    private fun handleCharacterSelected(char: Character) {
        char.selected = !char.selected
        val newList = Helper.getResubmitList(char, characterList.value!!)
        characterList.value = newList
    }

    private fun handleCharacterDeselected() {
        for(charId in oldSnippet.characterList) {
            if(!snippet.characterList.contains(charId)) {
                val character = Main.getCharacter(charId)
                if(character != null) {
                    character.snippetsList.remove(snippet.id)
                    FireChars.update(character.id, "snippetsList", character.snippetsList)
                }
            }
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
        for(c in characterList.value!!) {
            if(c.selected) {
                if(!snippet.characterList.contains(c.id)) snippet.characterList.add(c.id)
                if(!c.snippetsList.contains(snippet.id)) {
                    c.snippetsList.add(snippet.id)
                    FireChars.update(c.id, "snippetsList", c.snippetsList)
                }
            }
            else {
                if(snippet.characterList.contains(c.id)) {
                    snippet.characterList.remove(c.id)
                }
            }
        }
        for(w in snippet.getWords()) {
            Log.i("deselectTest", "word is ${w.text} | id: ${w.id} | !w.snippetsList.contains(snippet.id) = ${!w.snippetsList.contains(snippet.id)}")
            if(!w.snippetsList.contains(snippet.id)) {
                w.snippetsList.add(snippet.id)
                w.increaseWordUsed()
                FireWords.update(w.id, "snippetsList", w.snippetsList)
            }
        }
        FireSnippets.add(snippet, context)
        characterList.value = mutableListOf()
        WordConnection.connect(snippet)
        handleWordDeselected()
        handleCharacterDeselected()
    }

    private fun setWordIcon() {
        if(snippet.wordList.isEmpty()) {
            b.wordIcon.visibility = View.VISIBLE
            b.associatedWords.visibility = View.GONE
        }
        else {
            b.wordIcon.visibility = View.GONE
            b.associatedWords.visibility = View.VISIBLE
        }
    }


    private fun setContent() {
        b.snippetInput.setText(snippet.content)
       b.associatedWords.text =  Helper.setWordsToMultipleLines(snippet.getWords())
    }

    private fun setObserver() {
        liveWordList.observe(context as LifecycleOwner) {
            val selectedWords = mutableListOf<Word>()
            for(w  in it) {
                if(w.selected && !selectedWords.contains(w)) selectedWords.add(w)
            }
            b.associatedWords.text = Helper.setWordsToString(selectedWords)
            setWordIcon()
        }

        characterList.observe(context as LifecycleOwner) {
            val selectedChars = mutableListOf<Character>()
            for(c in it) {
                if(c.selected && !selectedChars.contains(c)) selectedChars.add(c)
            }
            charAdapter.submitList(selectedChars)
        }
    }

    private fun setRecycler() {
        charAdapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        b.characterRecycler.adapter = charAdapter
        charAdapter.submitList(snippet.getCharacters())

    }


}