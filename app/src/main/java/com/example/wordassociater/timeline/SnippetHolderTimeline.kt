package com.example.wordassociater.timeline

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.HolderSnippetBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.snippets.ConnectSnippetsFragment
import com.example.wordassociater.snippets.SnippetFragment
import com.example.wordassociater.utils.Date

class SnippetHolderTimeline(val b: HolderSnippetBinding, val onSnippetSelected: (snippet : Snippet) -> Unit): RecyclerView.ViewHolder(b.root) {
    lateinit var snippet : Snippet
    fun onBind(snippet: Snippet) {
        this.snippet = snippet
        setContent()
        setClickListener()
        setBackground()
        setRecycler()
        setBackGroundColorFirstTime()
    }

    private fun setContent() {
        b.textFieldId.text = snippet.id.toString()
        b.contentPreview.text = snippet.content

        b.dateHolder.setDateHolder(snippet.date, snippet)
        b.storyLineRecycler.visibility = if(snippet.storyLineList.isNotEmpty()) View.VISIBLE else View.GONE
        Log.i("dateProb", "is date visible? ${b.dateHolder.isVisible}")
        setObserver()
        checkWordList()
    }

    private fun checkWordList() {
        var wordString = ""
        for(w in snippet.getWords()) {
            wordString += "${w.text} "
        }
        Log.i("dateProb", "is date visible? last call ${b.dateHolder.isVisible}")
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
                }
                snippet -> {
                }
                else -> {
                    connectSnippets(SnippetFragment.selectedSnippet!!, snippet)
                }
            }
        }

        b.root.setOnClickListener {
            onSnippetSelected(snippet)
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
        ConnectSnippetsFragment.snippetOne = snippetOne
        ConnectSnippetsFragment.snippetTwo = snippetTwo
        SnippetFragment.navController.navigate(R.id.action_snippetFragment_to_connectSnippetsFragment)
    }

    private fun setRecycler() {
        val adapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW, fromStory = true)
        b.characterRecycler.adapter = adapter
        val notAnyChar = snippet.getCharacters().filter { c -> c.id != 22L }
        adapter.submitList(notAnyChar)
        if(snippet.characterList.isNotEmpty()) b.characterRecycler.visibility = View.VISIBLE

        val liveList = MutableLiveData<List<StoryLine>>()
        b.storyLineRecycler.initRecycler(liveList, true)
        liveList.value = snippet.getStoryLines()

        b.wordsRecycler.visibility = if(snippet.wordList.isEmpty()) View.GONE else View.VISIBLE
        b.wordsRecycler.initRecycler(MutableLiveData(), fromStory = true)
        val notAnyWord = snippet.getWords().filter { w -> w.id != 0L }
        b.wordsRecycler.submitList(notAnyWord)
    }

    private fun setObserver() {
        DisplayFilter.observeConnectShown(b.root.context, b.connectBtn)
        DisplayFilter.observeLinesShown(b.root.context, listOf(b.lineSmall, b.lineBig))
        DisplayFilter.observeDateShown(b.root.context, b.dateHolder)
        DisplayFilter.observeContentShown(b.root.context, b.contentPreview)
        DisplayFilter.observeTitleShown(b.root.context, b.headerText)
        DisplayFilter.observeCharacterShown(b.root.context, b.characterRecycler)
        DisplayFilter.observeDeleteShown(b.root.context, b.btnDelete)
        DisplayFilter.observeWordsShown(b.root.context, b.wordsRecycler)
        DisplayFilter.observeDateShown(b.root.context, b.dateHolder)
        DisplayFilter.observeLayerShown(b.root.context, b.layerButton)
        DisplayFilter.observeStoryLineShown(b.root.context, b.storyLineRecycler)
        DisplayFilter.observeItemColorDark(b.root.context, b.root,  listOf(b.headerText, b.contentPreview))
    }

    private fun setBackGroundColorFirstTime() {
        var dark = DisplayFilter.itemColorDark.value!!
        b.root.setBackgroundColor(if(dark) b.root.context.resources.getColor(R.color.snippets) else b.root.context.resources.getColor(R.color.white))
        b.contentPreview.setTextColor(if(dark) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
        b.headerText.setTextColor(if(dark) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
    }

}
