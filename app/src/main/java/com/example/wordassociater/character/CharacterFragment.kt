package com.example.wordassociater.character

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
import com.example.wordassociater.ViewPagerFragment
import com.example.wordassociater.databinding.FragmentCharacterBinding
import com.example.wordassociater.dialogue.DialogueAdapter
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Dialogue
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.snippets.SnippetAdapter
import com.example.wordassociater.strains.StrainAdapter
import com.example.wordassociater.utils.Page

class CharacterFragment: Fragment() {
    lateinit var b: FragmentCharacterBinding

    private val characterSnippetsAdapter = SnippetAdapter(::snippetClickedFunc)
    private val characterStrainAdapter = StrainAdapter(::handleStrainClickedFunc)
    private val dialogueAdapter = DialogueAdapter(::onDialogueSelected)

    companion object {
        var character = Character("")
    }

    private fun handleStrainClickedFunc(strain: Strain) {

    }

    private fun snippetClickedFunc(snippet: Snippet) {

    }

    private fun onDialogueSelected(dialogue: Dialogue) {

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
        b.textCharacterName.text = character.name
    }

    private fun setClickListener() {
        b.backButton.setOnClickListener {
            ViewPagerFragment.comingFrom = Page.Chars
            findNavController().navigate(R.id.action_characterFragment_to_ViewPagerFragment)
        }

        b.characterPortrait.setOnClickListener {
            b.specificCharacterRecycler.visibility = View.GONE
            b.characterDescriptionLinear.visibility = View.VISIBLE
        }

        b.buttonSnippets.setOnClickListener {
            b.specificCharacterRecycler.visibility = View.VISIBLE
            b.characterDescriptionLinear.visibility = View.GONE
            b.specificCharacterRecycler.adapter = characterSnippetsAdapter
            getAndSubmitSnippets()
        }

        b.buttonStrains.setOnClickListener {
            b.specificCharacterRecycler.visibility = View.VISIBLE
            b.characterDescriptionLinear.visibility = View.GONE
            getAndSubmitStrains()
        }

        b.buttonDialogues.setOnClickListener {
            b.specificCharacterRecycler.visibility = View.VISIBLE
            b.characterDescriptionLinear.visibility = View.GONE
            getAndSubmitDialogues()
        }
    }

    private fun getAndSubmitSnippets() {
        b.specificCharacterRecycler.adapter = characterSnippetsAdapter
        val snippetList = mutableListOf<Snippet>()
        for(snippet in Main.snippetList.value!!) {
            if(snippet.characterList.contains(character.id)) {
                snippetList.add(snippet)
            }
        }
        characterSnippetsAdapter.submitList(snippetList)
    }

    private fun getAndSubmitStrains() {
        b.specificCharacterRecycler.adapter = characterStrainAdapter
        for (strain in Main.strainsList.value!!) {
            val characterStrainList = mutableListOf<Strain>()
            if (strain.characterList.contains(character.id)) {
                characterStrainList.add(strain)
            }
            characterStrainAdapter.submitList(characterStrainList)
        }
    }

    private fun getAndSubmitDialogues() {
        b.specificCharacterRecycler.adapter = dialogueAdapter
        val dialogueList = mutableListOf<Dialogue>()
        for(dialogue in Main.dialogueList.value!!) {
            if(dialogue.characterList.contains(character.id)) {
                dialogueList.add(dialogue)
            }
        }
        dialogueAdapter.submitList(dialogueList)
    }


}