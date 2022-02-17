package com.example.wordassociater.word_detailed_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentWordDetailedBinding
import com.example.wordassociater.strain_list_fragment.StrainAdapter
import com.example.wordassociater.snippet_fragment.SnippetAdapter
import com.example.wordassociater.start_fragment.Word
import com.example.wordassociater.firestore.FireLists
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.Snippet
import com.example.wordassociater.strain_edit_fragment.Strain

class WordDetailedFragment: Fragment() {
    lateinit var b: FragmentWordDetailedBinding

    companion object {
        lateinit var word: Word
        lateinit var strainAdapter: StrainAdapter
        lateinit var snippetAdapter: SnippetAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentWordDetailedBinding.inflate(inflater)
        setClickListener()
        setContent()
        setRecycler()
        return b.root
    }

    private fun setContent() {
        b.wordText.text = word.text
        b.wordBgImage.setBackgroundResource(Helper.getWordBg(word.type))
        b.btnNewWord.visibility = View.GONE
    }

    private fun setClickListener() {
        b.backButton.setOnClickListener { findNavController().navigate(R.id.action_wordDetailedFragment_to_wordsListFragment) }

        b.buttonStrains.setOnClickListener {
            b.wordDetailedRecycler.adapter = strainAdapter
            getFilteredStrainsList()
        }

        b.buttonSnippets.setOnClickListener {
            b.wordDetailedRecycler.adapter = snippetAdapter
            getFilteredSnippetList()
        }
    }

    private fun getFilteredStrainsList() {
        FireLists.fireStrainsList.get().addOnSuccessListener { docs ->
            val strainsList = mutableListOf<Strain>()
            for(doc in docs) {
                val strain = doc.toObject(Strain::class.java)
                var contains = false
                for(w in strain.wordList!!) {
                    if(word.id == w.id) contains = true ; break
                }
                if(contains) strainsList.add(strain)
            }
            strainAdapter.submitList(strainsList)
        }
    }

    private fun getFilteredSnippetList() {
        FireLists.snippetsList.get().addOnSuccessListener { docs ->
            val snippetsList = mutableListOf<Snippet>()
            for(doc in docs) {
                val snippet = doc.toObject(Snippet::class.java)
                var contains = false
                for(w in snippet.wordsUsed) {
                    if(word.id == w.id) contains = true ; break
                }
                if(contains) snippetsList.add(snippet)
            }
            snippetAdapter.submitList(snippetsList)
        }
    }

    private fun setRecycler() {
        strainAdapter = StrainAdapter()
        snippetAdapter = SnippetAdapter()
        b.wordDetailedRecycler.adapter = strainAdapter

    }
}