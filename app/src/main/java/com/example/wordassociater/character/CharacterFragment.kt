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
import com.example.wordassociater.ViewPagerFragment
import com.example.wordassociater.databinding.FragmentCharacterBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.firestore.FireLists
import com.example.wordassociater.snippets.SnippetAdapter
import com.example.wordassociater.strains.StrainAdapter
import com.example.wordassociater.utils.Page

class CharacterFragment: Fragment() {
    lateinit var b: FragmentCharacterBinding

    companion object {
        val characterSnippetsAdapter = SnippetAdapter(::snippetClickedFunc)
        val characterStrainAdapter = StrainAdapter(::handleStrainClickedFunc)

        var character = Character("")

        private fun handleStrainClickedFunc(strain: Strain) {

        }

        private fun snippetClickedFunc(snippet: Snippet) {

        }
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
        if(character.imgUrl != "") {
            Glide.with(b.characterPortrait).load(character.imgUrl).into(b.characterPortrait)
        }
        b.characterName.text = character.name
    }

    private fun setClickListener() {
        b.backButton.setOnClickListener {
            ViewPagerFragment.comingFrom = Page.Chars
            findNavController().navigate(R.id.action_characterFragment_to_ViewPagerFragment)
        }

        b.buttonSnippets.setOnClickListener {
            b.specificCharacterRecycler.adapter = characterSnippetsAdapter
            FireLists.snippetsList.get().addOnSuccessListener { docs ->
                val characterSnippetList = mutableListOf<Snippet>()
                if(docs != null) {
                    for(doc in docs) {
                        val snippet = doc.toObject(Snippet::class.java)
                        if(snippet.characterList.contains(character.id)) {
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
                    if(strain.characterList.contains(character.id)) {
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