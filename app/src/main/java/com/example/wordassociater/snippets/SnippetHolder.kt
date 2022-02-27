package com.example.wordassociater.snippets

import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.HolderSnippetBinding
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.utils.Date

class SnippetHolder(val b: HolderSnippetBinding, val adapter: SnippetAdapter,
                    val clickSnippetFunc: (snippet:Snippet) -> Unit): RecyclerView.ViewHolder(b.root) {
    lateinit var snippet : Snippet
    fun onBind(snippet: Snippet) {
        this.snippet = snippet
        setContent()

        setClickListener()
        setBackground()
        setRecycler()
    }

    private fun setContent() {
        b.textFieldId.text = snippet.id.toString()
        b.contentPreview.text = snippet.content
        b.wordsRecycler.initRecycler(MutableLiveData())
        b.wordsRecycler.submitList(snippet.getWords())
        b.dateHolder.setDateHolder(snippet.date, snippet)
        checkWordList()

    }

    private fun checkWordList() {
        var wordString = ""
        for(w in snippet.getWords()) {
            wordString += "${w.text} "
        }
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
            snippet.delete()
        }
    }

    private fun setClickListener() {
        b.btnDelete.setOnClickListener {
            Pop(b.btnDelete.context).confirmationPopUp(b.btnDelete, ::deleteSnippet)
        }
        b.connectBtn.setOnClickListener {
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
            clickSnippetFunc(snippet)
        }
    }

    private fun onDateEntered(date: Date) {
        FireSnippets.update(snippet.id, "date", date)
    }

    private fun connectSnippets(snippetOne: Snippet, snippetTwo: Snippet) {
        if(!snippetOne.connectedSnippetsList.contains(snippetTwo.id)) {
            snippetOne.connectedSnippetsList.add(snippetTwo.id)
            snippetTwo.connectedSnippetsList.add(snippetOne.id)
            FireSnippets.update(snippetTwo.id, "connectedSnippets", snippetTwo.connectedSnippetsList)
            FireSnippets.update(snippetOne.id, "connectedSnippets", snippetOne.connectedSnippetsList)
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
        adapter.submitList(snippet.getCharacters())
        if(snippet.characterList.isNotEmpty()) b.characterRecycler.visibility = View.VISIBLE
    }

}