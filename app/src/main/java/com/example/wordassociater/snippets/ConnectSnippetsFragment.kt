package com.example.wordassociater.snippets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.firestore.FireLists
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.utils.Helper

class ConnectSnippetsFragment: Fragment() {
    lateinit var b: FragmentConnectSnippetsBinding
    val newStrain = Strain()
    companion object {
        lateinit var snippetOne : Snippet
        lateinit var snippetTwo : Snippet
        lateinit var adapter: CharacterAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Main.inFragment = Frags.CONNECTSNIPPETS
        b = FragmentConnectSnippetsBinding.inflate(inflater)
        setStrain()
        setContent()
        setClickListener()
        return b.root
    }

    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_connectSnippetsFragment_to_snippetFragment)
        }

        b.saveBtn.setOnClickListener {
            saveStrain()
        }

        b.characterBtn.setOnClickListener {
            Pop(b.characterBtn.context).characterRecyclerConnectSnippets(b.characterBtn, newStrain.getCharacters())
        }
    }

    private fun setStrain() {
        newStrain.content = snippetOne.content + " " + snippetTwo.content
        makeWordsList()
        makeCharacterList()
        handleRecycler()
    }

    private fun makeWordsList() {
        val wordsList = mutableListOf<String>()
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
        newStrain.wordList = wordsList
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

        newStrain.characterList = characterList
    }

    private fun setContent() {
        b.associatedWords.text = Helper.setWordsToString(newStrain.getWords())
        b.strainInput.setText(newStrain.content)
        handleCharacter()
    }

    private fun handleCharacter() {
        if(newStrain.characterList.isNotEmpty() && Main.getCharacter(newStrain.characterList[0])?.imgUrl != "") {
            Glide.with(b.characterBtn.context).load(Main.getCharacter(newStrain.characterList[0])?.imgUrl).into(b.characterBtn)
        }
    }

    private fun handleRecycler() {
        adapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        b.characterRecycler.adapter = adapter
        if(newStrain.characterList.isNotEmpty()) {
            adapter.submitList(Character.getCharactersById(newStrain.characterList))
        }
    }

    private fun saveStrain() {
        newStrain.content = b.strainInput.text.toString()
        newStrain.header = b.headerInput.text.toString()

        FireLists.fireStrainsList.add(newStrain).addOnSuccessListener {
            Toast.makeText(context, "New Strain Created", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to make new Strain", Toast.LENGTH_SHORT).show()
        }
        findNavController().navigate(R.id.action_connectSnippetsFragment_to_startFragment)
    }
}