package com.example.wordassociater.snippets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentEditSnippetBinding
import com.example.wordassociater.fire_classes.*
import com.example.wordassociater.live_recycler.LiveRecycler
import com.example.wordassociater.popups.popLiveClass
import com.example.wordassociater.popups.popNuwsEdit
import com.example.wordassociater.popups.popSearchWord
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.LiveClass
import com.example.wordassociater.words.WordRecycler

class EditSnippetFragment: Fragment() {
    lateinit var b : FragmentEditSnippetBinding

    companion object {
        var oldSnippet = Snippet()
        lateinit var snippet: Snippet
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentEditSnippetBinding.inflate(inflater)

        handleLiveLists()
        setTopBar()
        setClickListener()
        setWordsInput()
        setRecycler()
        setSnippetPartsBar()
        setPreviewBar()

        return b.root
    }

    private fun setPreviewBar() {
        b.previewBar.initBar(snippet)
    }

    private fun handleLiveLists() {
        snippet.copyMe()
        snippet.getFullCharacterList()
        snippet.getFullStoryLineList()
        snippet.getFullItemList()
        snippet.getFullItemList()
    }

    private fun setSnippetPartsBar() {
        b.snippetPartsBar.initSnippetsBar()
        b.snippetPartsBar.setCharacterPopup(snippet.liveCharacter, ::onCharacterSelected)
        b.snippetPartsBar.setLocationsPopup(snippet.liveLocations, ::onLocationSelected)
        b.snippetPartsBar.setEventsPopup(snippet.liveEvents, ::onEventSelected)
        b.snippetPartsBar.setItemsPopup(snippet.liveItems, ::onItemSelected)
    }

    private fun setWordsInput() {
        b.snippetInput.setStoryPart(snippet)
        b.snippetInput.setTextField(snippet.content)
    }

    private fun setTopBar() {
        b.topBar.setBtn4IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn3IconAndVisibility(R.drawable.icon_word, false)
    }

    private fun setClickListener() {
        b.topBar.setRightButton {
            saveSnippet()
            findNavController().navigate(R.id.action_editSnippetFragment_to_snippetFragment)
        }

        b.topBar.setLeftBtn {
            snippet.wordList = oldSnippet.wordList
            snippet.characterList = oldSnippet.characterList
            findNavController().navigate(R.id.action_editSnippetFragment_to_snippetFragment)
        }

        b.topBar.setBtn2 {
            snippet.getFullWordsList()
            popSearchWord(b.topBar.btn2, ::onWordSelected , snippet.liveWordsSearch, ::onHeaderSelected)
        }

        b.topBar.setBtn1 {
            b.snippetInput.getNuwsForPopup { liveList, onUpgradeClicked, onDirtClicked, onPotatoClicked ->
                popNuwsEdit(b.topBar.btn1, liveList, onUpgradeClicked, onDirtClicked, onPotatoClicked, ::addNuwWordToSnippet )
            }
        }

        b.topBar.setBtn5 {
            popLiveClass( LiveRecycler.Type.StoryLine, b.topBar.btn5, snippet.liveSelectedStoryLines, ::onStoryLineSelected)
        }
    }

    private fun addNuwWordToSnippet(nuw: Nuw) {
        if(nuw.isWord) {
            val word = Main.getWordByText(nuw.text)
            if(word != null) {
                onWordSelected(word)
                b.snippetInput.updateNuwsList()
            }
            else {
                Helper.toast("Word ${nuw.text} not found. this should not happen", b.root.context)
            }
        }

        else {
            nuw.upgradeToWord()
        }
    }

    private fun onCharacterSelected(char: LiveClass) {
        snippet.takeCharacter(char as Character)
    }

    private fun onStoryLineSelected(storyLine: LiveClass) {
        snippet.takeStoryLine(storyLine as StoryLine)
    }

    private fun onWordSelected(word: Word) {
        snippet.takeWord(word)
    }

    private fun onHeaderSelected(wordText: String) {

    }

    private fun onEventSelected(event: LiveClass) {
        snippet.takeEvent(event as Event)
    }

    private fun onItemSelected(item: LiveClass) {
        snippet.takeItem(item as Item)
    }

    private fun onLocationSelected(location: LiveClass) {
        snippet.takeLocation(location as Location)
    }

    private fun saveSnippet() {
        snippet.updateContent(b.snippetInput.content)
        b.snippetInput.saveNuws()
        snippet.updateCharacter()
        snippet.updateWords()
        snippet.updateEvents()
        snippet.updateItems()
        snippet.updateLocation()
        snippet.updateStoryLines()
        WordConnection.connect(snippet)
    }


    private fun setRecycler() {
        b.wordPreviewRecycler.initRecycler(WordRecycler.Mode.Preview, null, null , null)
        b.wordPreviewRecycler.setLiveList(snippet.getWords())

    }


}