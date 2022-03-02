package com.example.wordassociater.snippets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.FragmentConnectSnippetsBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.utils.Helper

class ConnectSnippetsFragment: Fragment() {
    lateinit var b: FragmentConnectSnippetsBinding
    val newSnippet = Snippet(id = FireStats.getStoryPartId())
    companion object {
        lateinit var snippetOne : Snippet
        lateinit var snippetTwo : Snippet
        lateinit var adapter: CharacterAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Main.inFragment = Frags.CONNECTSNIPPETS
        b = FragmentConnectSnippetsBinding.inflate(inflater)
        setSnippet()
        setContent()
        setClickListener()
        return b.root
    }

    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_connectSnippetsFragment_to_snippetFragment)
        }

        b.saveBtn.setOnClickListener {
            saveSnippet()
        }

        b.characterBtn.setOnClickListener {
        }
    }

    private fun setSnippet() {
        newSnippet.content = snippetOne.content + " " + snippetTwo.content
        makeWordsList()
        makeCharacterList()
        handleRecycler()
    }

    private fun makeWordsList() {
        val wordsList = mutableListOf<Long>()
        for(w in snippetOne.wordList) {
            if(!wordsList.contains(w)) {
                wordsList.add(w)
            }
        }
        for(w in snippetTwo.wordList) {
            if(!wordsList.contains(w)) {
                wordsList.add(w)
            }
        }
        newSnippet.wordList = wordsList
    }

    private fun makeCharacterList() {
        val characterList = mutableListOf<Long>()
        for(c in snippetOne.characterList) {
            if(!characterList.contains(c)) {
                characterList.add(c)
            }
        }
        for(c in snippetTwo.characterList) {
            if(!characterList.contains(c)) {
                characterList.add(c)
            }
        }

        newSnippet.characterList = characterList
    }

    private fun setContent() {
        b.associatedWords.text = Helper.setWordsToString(newSnippet.getWords())
        b.strainInput.setText(newSnippet.content)
        handleCharacter()
    }

    private fun handleCharacter() {
        if(newSnippet.characterList.isNotEmpty() && Main.getCharacter(newSnippet.characterList[0])?.imgUrl != "") {
            Glide.with(b.characterBtn.context).load(Main.getCharacter(newSnippet.characterList[0])?.imgUrl).into(b.characterBtn)
        }
    }

    private fun handleRecycler() {
        adapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        b.characterRecycler.adapter = adapter
        if(newSnippet.characterList.isNotEmpty()) {
            adapter.submitList(Character.getCharactersById(newSnippet.characterList))
        }
    }

    private fun saveSnippet() {
        newSnippet.content = b.strainInput.text.toString()
        newSnippet.header = b.headerInput.text.toString()

        FireSnippets.add(newSnippet, context)
    }
}