package com.example.wordassociater.snippets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterRecycler
import com.example.wordassociater.databinding.FragmentEditSnippetBinding
import com.example.wordassociater.fire_classes.*
import com.example.wordassociater.popups.popNuwsEdit
import com.example.wordassociater.popups.popSearchWord
import com.example.wordassociater.popups.popSelectStoryLine
import com.example.wordassociater.utils.Helper
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

        setTopBar()
        setClickListener()
        setWordsInput()
        setRecycler()
        setSnippetPartsBar()

        return b.root
    }

    private fun handleLiveLists() {
        snippet.copyMe()
        snippet.getFullCharacterList()
        snippet.getFullStoryLineList()
        snippet.getFullItemList()
        snippet.getFullItemList()
    }

    private fun setSnippetPartsBar() {
        b.snippetPartsBar.setTheSnippet(snippet)
        b.snippetPartsBar.setCharacterPopup()
        b.snippetPartsBar.setLocationsPopup()
        b.snippetPartsBar.setEventsPopup()
        b.snippetPartsBar.setItemsPopup()
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
            popSearchWord(b.topBar.btn2, ::onWordSelected , snippet.liveWords)
        }

        b.topBar.setBtn1 {
            b.snippetInput.getNuwsForPopup { liveList, onUpgradeClicked, onDirtClicked, onPotatoClicked ->
                popNuwsEdit(b.topBar.btn1, liveList, onUpgradeClicked, onDirtClicked, onPotatoClicked, ::addNuwWordToSnippet )
            }
        }

        b.topBar.setBtn5 {
            popSelectStoryLine(b.topBar.btn5, ::onStoryLineSelected, snippet.liveStoryLines, fromMiddle = false)
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

    private fun handleCharacterSelected(char: Character) {
        snippet.takeCharacter(char)
    }

    private fun onStoryLineSelected(storyLine: StoryLine) {
        snippet.takeStoryLine(storyLine)
    }

    private fun onWordSelected(word: Word) {
        Log.i("lagProb", "EditSnippet/onWordSelected called")
        snippet.takeWord(word)
    }

    private fun onEventSelected(event: Event) {
        snippet.takeEvent(event)
    }

    private fun onItemSelected(item: Item) {
        snippet.takeItem(item)
    }

    private fun onLocationSelected(location: Location) {
        snippet.takeLocation(location)
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
        b.characterRecycler.initRecycler(CharacterRecycler.Mode.Preview, snippet.liveCharacter , null)
        b.wordPreviewRecycler.initRecycler(WordRecycler.Mode.Preview, null, null )
        b.wordPreviewRecycler.setLiveList(snippet.getWords())

    }


}