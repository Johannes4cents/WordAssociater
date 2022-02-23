package com.example.wordassociater.story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentCharacterBinding
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.firestore.FireLists
import com.example.wordassociater.snippets.SnippetAdapter
import com.example.wordassociater.strains.StrainAdapter

class StoryFragment: Fragment() {
    lateinit var b : FragmentCharacterBinding
    lateinit var strainAdapter: StrainAdapter
    lateinit var snippetAdapter: SnippetAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentCharacterBinding.inflate(inflater)
        setContent()
        setRecycler()
        setClickListener()
        return b.root

    }

    private fun setContent() {
        b.characterPortrait.setImageResource(R.drawable.icon_story)
        b.characterName.text = "Story"
        b.characterName.textSize = 24f
    }
    private fun setClickListener() {
        b.buttonStrains.setOnClickListener {
            b.specificCharacterRecycler.adapter = strainAdapter

            FireLists.fireStrainsList.get().addOnSuccessListener { docs ->
                val strainList = mutableListOf<Strain>()
                for(doc in docs) {
                    val strain = doc.toObject(Strain::class.java)
                    if(strain.isStory) strainList.add(strain)
                }
                strainAdapter.submitList(strainList)
            }
        }

        b.buttonSnippets.setOnClickListener {
            b.specificCharacterRecycler.adapter = snippetAdapter
            FireLists.snippetsList.get().addOnSuccessListener { docs ->
                val snippetsList = mutableListOf<Snippet>()
                for(doc in docs) {
                    val snippet = doc.toObject(Snippet::class.java)
                    if(snippet.isStory) snippetsList.add(snippet)
                }
                snippetAdapter.submitList(snippetsList)
            }
        }

        b.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_storyFragment_to_startFragment)
        }
    }

    private fun setRecycler() {
        strainAdapter = StrainAdapter(::handleStrainClickedFunc)
        snippetAdapter = SnippetAdapter(::snippedClickedFunc)
        b.specificCharacterRecycler.adapter = strainAdapter

        FireLists.fireStrainsList.get().addOnSuccessListener { docs ->
            val strainList = mutableListOf<Strain>()
            for(doc in docs) {
                val strain = doc.toObject(Strain::class.java)
                if(strain.isStory) strainList.add(strain)
            }
            strainAdapter.submitList(strainList)
        }
    }

    private fun handleStrainClickedFunc(strain: Strain) {

    }

    private fun snippedClickedFunc(snippet: Snippet) {

    }
}