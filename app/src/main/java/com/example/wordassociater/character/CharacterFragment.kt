package com.example.wordassociater.character

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
import com.example.wordassociater.databinding.FragmentCharacterBinding
import com.example.wordassociater.strain_list_fragment.StrainAdapter
import com.example.wordassociater.snippet_fragment.SnippetAdapter
import com.example.wordassociater.firestore.FireLists
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.Strain

class CharacterFragment: Fragment() {
    lateinit var b: FragmentCharacterBinding

    companion object {
        val characterSnippetsAdapter = SnippetAdapter()
        val characterStrainAdapter = StrainAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Main.inFragment = Frags.CHARACTER
        b = FragmentCharacterBinding.inflate(layoutInflater)
        handleRecycler()
        setContent()
        setClickListener()
        getAndSubmitStrains()
        return b.root
    }

    private fun handleRecycler() {
        b.specificCharacterRecycler.adapter = characterStrainAdapter
    }

    private fun setContent() {
        var imgUrl = ""
        if(CharacterListFragment.selectedCharacter.value != null) imgUrl = CharacterListFragment.selectedCharacter.value?.imgUrl!!
        if(imgUrl != "") {
            Glide.with(b.characterPortrait).load(imgUrl).into(b.characterPortrait)
        }
        b.characterName.text = CharacterListFragment.selectedCharacter.value!!.name
    }

    private fun setClickListener() {
        b.backButton.setOnClickListener {
            CharacterListFragment.selectedCharacter.value = null
            findNavController().navigate(R.id.action_characterFragment_to_characterListFragment)
        }

        b.buttonSnippets.setOnClickListener {
            b.specificCharacterRecycler.adapter = characterSnippetsAdapter
            FireLists.snippetsList.get().addOnSuccessListener { docs ->
                val characterSnippetList = mutableListOf<Snippet>()
                if(docs != null) {
                    for(doc in docs) {
                        val snippet = doc.toObject(Snippet::class.java)
                        if(snippet.characterList.contains(CharacterListFragment.selectedCharacter.value)) {
                            characterSnippetList.add(snippet)
                        }
                    }
                    characterSnippetsAdapter.submitList(characterSnippetList)
                }
                else {
                    Toast.makeText(context, "no snippets received", Toast.LENGTH_SHORT).show()
                }
            }
        }

        b.buttonStrains.setOnClickListener {
            getAndSubmitStrains()
        }
    }

    private fun getAndSubmitStrains() {
        b.specificCharacterRecycler.adapter = characterStrainAdapter
        FireLists.fireStrainsList.get().addOnSuccessListener { docs ->
            val characterStrainList = mutableListOf<Strain>()
            if(docs != null) {
                for(doc in docs) {
                    val strain = doc.toObject(Strain::class.java)
                    strain.id = doc.id
                    if(strain.characterList.contains(CharacterListFragment.selectedCharacter.value)) {
                        characterStrainList.add(strain)
                    }
                }
                characterStrainAdapter.submitList(characterStrainList)
            }
            else {
                Toast.makeText(context, "no snippets received", Toast.LENGTH_SHORT).show()
            }
        }
    }


}