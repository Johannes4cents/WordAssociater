package com.example.wordassociater.snippets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.FragmentEditSnippetBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.utils.Helper

class EditSnippetFragment: Fragment() {
    lateinit var b : FragmentEditSnippetBinding
    lateinit var charAdapter: CharacterAdapter
    private val liveWordList = MutableLiveData<MutableList<Word>>()
    private val newlyCreatedWordsList = mutableListOf<Word>()
    companion object {
        lateinit var snippet: Snippet
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentEditSnippetBinding.inflate(inflater)
        setClickListener()
        setContent()
        setRecycler()
        setObserver()
        return b.root
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
            Pop(b.characterBtn.context).characterRecycler( b.characterBtn , ::handleCharacterSelected)
        }

        b.associatedWords.setOnClickListener {
            Pop(b.associatedWords.context).wordRecycler(b.associatedWords, ::handleWordClick, ::deleteUsedWord, liveWordList, ::addNewWordToList)
        }
    }

    private fun handleWordClick(word: Word) {

    }

    private fun saveSnippet() {
        for(w in newlyCreatedWordsList) {
            if(!Helper.checkIfWordExists(w, requireContext())) FireWords.add(w)
            else snippet.wordList.remove(w.id)
        }
        snippet.content = b.snippetInput.text.toString()
        FireSnippets.update(snippet, context)
    }

    private fun addNewWordToList(word: Word) {
        newlyCreatedWordsList.add(word)
        snippet.wordList.add(word.id)
        liveWordList.value = snippet.getWords()
    }

    private fun deleteUsedWord(word: Word) {
        snippet.wordList.remove(word.id)
        newlyCreatedWordsList.remove(word)
        liveWordList.value = snippet.getWords()
    }

    private fun setContent() {
        b.snippetInput.setText(snippet.content)
        b.associatedWords.text =  Helper.setWordsToMultipleLines(snippet.getWords())
        liveWordList.value = snippet.getWords()
    }

    private fun setObserver() {
        liveWordList.observe(context as LifecycleOwner) {
            b.associatedWords.text = Helper.setWordsToString(it)
        }
    }

    private fun setRecycler() {
        charAdapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        b.characterRecycler.adapter = charAdapter
        charAdapter.submitList(snippet.getCharacters())

    }

    private fun handleCharacterSelected(char: Character) {
        val charInSnippet = snippet.characterList.find { id -> id == char.id }
        if(charInSnippet != null ) {
            snippet.characterList.remove(charInSnippet)
        }
        else snippet.characterList.add(char.id)
        charAdapter.submitList(snippet.getCharacters())
        charAdapter.notifyDataSetChanged()
    }
}