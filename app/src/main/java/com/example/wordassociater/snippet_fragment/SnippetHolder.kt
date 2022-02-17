package com.example.wordassociater.snippet_fragment

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.connect_snippets_fragment.ConnectSnippetsFragment
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.HolderSnippetBinding
import com.example.wordassociater.edit_snippet_fragment.EditSnippetFragment
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.fire_classes.Snippet

class SnippetHolder(val b: HolderSnippetBinding, val adapter: SnippetAdapter): RecyclerView.ViewHolder(b.root) {
    lateinit var snippet : Snippet
    fun onBind(snippet: Snippet) {
        this.snippet = snippet
        Log.i("snippet", "SnippetHolder/onBind")
        setContent()

        setClickListener()
        setBackground()
        setRecycler()
    }

    private fun setContent() {
        b.snippetContent.text = snippet.content
        b.associatedWordsSnippet.text = Helper.setWordsToMultipleLines(snippet.wordsUsed)
        checkWordList()

    }

    private fun checkWordList() {
        var wordString = ""
        for(w in snippet.wordsUsed) {
            wordString += "${w.text} "
        }
        Toast.makeText(b.root.context, "Words are: $wordString", Toast.LENGTH_SHORT).show()
    }

    private fun setBackground() {
        if(snippet == SnippetFragment.selectedSnippet) {
            b.root.setBackgroundColor(b.root.resources.getColor(R.color.gold))
        }
        else {
            b.root.setBackgroundColor(b.root.resources.getColor(R.color.white))
        }
    }

    private fun deleteSnippet(confirmation: Boolean) {
        if(confirmation) {
            FireSnippets.delete(snippet)
        }
    }

    private fun setClickListener() {
        b.deleteSnippetIcon.setOnClickListener {
            Pop(b.deleteSnippetIcon.context).confirmationPopUp(b.deleteSnippetIcon, ::deleteSnippet)
        }
        b.connectSnippetsBtn.setOnClickListener {
            when (SnippetFragment.selectedSnippet) {
                null -> {
                    SnippetFragment.selectedSnippet = snippet
                    adapter.notifyDataSetChanged()

                }
                snippet -> {
                    SnippetFragment.selectedSnippet = null
                    adapter.notifyDataSetChanged()
                }
                else -> {
                    connectSnippets(SnippetFragment.selectedSnippet!!, snippet)
                }
            }

        }

        b.root.setOnClickListener {
            EditSnippetFragment.snippet = snippet
            SnippetFragment.navController.navigate(R.id.action_snippetFragment_to_editSnippetFragment)
        }
    }

    private fun connectSnippets(snippetOne: Snippet, snippetTwo: Snippet) {
        if(!snippetOne.connectedSnippets.contains(snippetTwo.id)) {
            snippetOne.connectedSnippets.add(snippetTwo.id)
            snippetTwo.connectedSnippets.add(snippetOne.id)
            FireSnippets.update(snippetTwo, b.root.context)
            FireSnippets.update(snippetOne, b.root.context)
        }
        else {
            Toast.makeText(b.root.context, "Snippets already \n connected", Toast.LENGTH_SHORT).show()
        }
        SnippetFragment.selectedSnippet = null
        adapter.notifyDataSetChanged()
        ConnectSnippetsFragment.snippetOne = snippetOne
        ConnectSnippetsFragment.snippetTwo = snippetTwo
        SnippetFragment.navController.navigate(R.id.action_snippetFragment_to_connectSnippetsFragment)
    }

    private fun setRecycler() {
        val adapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        b.characterRecycler.adapter = adapter
        adapter.submitList(snippet.characterList)
        if(snippet.characterList.isNotEmpty()) b.characterRecycler.visibility = View.VISIBLE
    }

}