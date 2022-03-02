package com.example.wordassociater.snippets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.FragmentEditSnippetBinding
import com.example.wordassociater.fire_classes.*
import com.example.wordassociater.firestore.FireChars
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.firestore.FireStoryLines
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.popups.popCharacterSelector
import com.example.wordassociater.popups.popNuwsEdit
import com.example.wordassociater.popups.popSearchWord
import com.example.wordassociater.popups.popSelectStoryLine
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.ListHelper

class EditSnippetFragment: Fragment() {
    lateinit var b : FragmentEditSnippetBinding
    lateinit var charAdapter: CharacterAdapter
    private val liveWordList = MutableLiveData<List<Word>>()
    private val liveStoryLineList = MutableLiveData<List<StoryLine>>()
    // Character
    private val characterList = MutableLiveData<List<Character>>()
    companion object {
        var oldSnippet = Snippet()
        lateinit var snippet: Snippet
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentEditSnippetBinding.inflate(inflater)
        ListHelper.handleSelectAndSetFullCharacterList(snippet, characterList)
        setWordList()
        setCharListOnOpen()
        setStorylineListonOpen()
        setStoryLineImage()
        setClickListener()
        setWordsInput()
        setRecycler()
        setObserver()
        b.wordPreviewRecycler.initRecycler(liveWordList)
        return b.root
    }

    private fun setWordsInput() {
        b.snippetInput.setStoryPart(snippet)
        b.snippetInput.setTextField(snippet.content)
    }

    private fun setStoryLineImage() {
        val selectedStoryLine = mutableListOf<StoryLine>()
        for (sl in liveStoryLineList.value!!) {
            if(sl.selected) selectedStoryLine.add(sl)
        }
        if(selectedStoryLine.isNotEmpty()) {
            b.topBar.btn5.setImageResource(selectedStoryLine[0].getIcon())
        }
        else {
            b.topBar.btn5.setImageResource(R.drawable.icon_story)
        }
    }


    private fun setWordList() {
        ListHelper.setWordList(snippet.getWords(), liveWordList)
    }

    private fun setCharListOnOpen() {
        val charList = Main.characterList.value!!.toMutableList()
        for(c in charList) {
            if(snippet.characterList.contains(c.id)) c.selected = true
        }
        characterList.value = charList
    }

    private fun setStorylineListonOpen() {
        val storyLineList = Main.storyLineList.value!!.toMutableList()
        for(sl in storyLineList) {
            sl.selected = snippet.storyLineList.contains(sl.id)
        }
        liveStoryLineList.value = storyLineList
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

        b.topBar.setBtn4 {
            setCharListOnOpen()
            popCharacterSelector(b.topBar.btn4, characterList, ::handleCharacterSelected)
        }

        b.topBar.setBtn2 {
            setWordList()
            popSearchWord(b.topBar.btn2, ::handleWordClick , liveWordList)
        }

        b.topBar.setBtn1 {
            b.snippetInput.getNuwsForPopup { liveList, onUpgradeClicked, onDirtClicked, onPotatoClicked ->
                popNuwsEdit(b.topBar.btn1, liveList, onUpgradeClicked, onDirtClicked, onPotatoClicked, ::addNuwWordToSnippet )
            }
        }

        b.topBar.setBtn5 {
            popSelectStoryLine(b.topBar.btn5, ::onStoryLineSelected, liveStoryLineList, fromMiddle = false)
        }
    }

    private fun onStoryLineSelected(storyLine: StoryLine) {
        storyLine.selected = !storyLine.selected
        liveStoryLineList.value = Helper.getResubmitList(storyLine, liveStoryLineList.value!!)
        setStoryLineImage()
    }

    private fun addNuwWordToSnippet(nuw: Nuw) {
        if(nuw.isWord) {
            val word = Main.getWordByText(nuw.text)
            if(word != null) {
                handleWordClick(word)
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

    private fun handleWordDeselected() {
        for(wordId in oldSnippet.wordList) {
            if(!snippet.wordList.contains(wordId)) {
                val word = Main.getWord(wordId)!!
                WordConnection.disconnect(word, snippet.id)
                word.snippetsList.remove(snippet.id)
                word.decreaseWordUsed()
                FireWords.update(word.id, "snippetsList", word.snippetsList)
            }
        }
    }

    private fun handleStoryLineDeselected() {
        for(storylineId in oldSnippet.storyLineList) {
            if(!snippet.storyLineList.contains(storylineId)) {
                val storyLine = Main.getStoryLine(storylineId)
                storyLine!!.snippetList.remove(snippet.id)
                FireStoryLines.update(storyLine.id, "snippetList", storyLine.snippetList)
            }
        }
    }

    private fun handleCharacterSelected(char: Character) {
        char.selected = !char.selected
        val newList = Helper.getResubmitList(char, characterList.value!!)
        characterList.value = newList
    }

    private fun handleCharacterDeselected() {
        for(charId in oldSnippet.characterList) {
            if(!snippet.characterList.contains(charId)) {
                val character = Main.getCharacter(charId)
                if(character != null) {
                    character.snippetsList.remove(snippet.id)
                    FireChars.update(character.id, "snippetsList", character.snippetsList)
                }
            }
        }
    }

    private fun handleWordClick(word: Word) {
        word.selected = !word.selected
        if(word.selected) {
            if(!snippet.wordList.contains(word.id)) snippet.wordList.add(word.id)
        }
        else snippet.wordList.remove(word.id)
        setWordList()
    }

    private fun saveSnippet() {
        snippet.content = b.snippetInput.content
        b.snippetInput.saveNuws()
        for(c in characterList.value!!) {
            if(c.selected) {
                if(!snippet.characterList.contains(c.id)) snippet.characterList.add(c.id)
                if(!c.snippetsList.contains(snippet.id)) {
                    c.snippetsList.add(snippet.id)
                    FireChars.update(c.id, "snippetsList", c.snippetsList)
                }
            }
            else {
                if(snippet.characterList.contains(c.id)) {
                    snippet.characterList.remove(c.id)
                    c.snippetsList.remove(c.id)
                    FireChars.update(c.id, "snippetsList", c.snippetsList)
                }
            }
        }
        for(w in snippet.getWords()) {
            Log.i("deselectTest", "word is ${w.text} | id: ${w.id} | !w.snippetsList.contains(snippet.id) = ${!w.snippetsList.contains(snippet.id)}")
            if(!w.snippetsList.contains(snippet.id)) {
                w.snippetsList.add(snippet.id)
                w.increaseWordUsed()
                FireWords.update(w.id, "snippetsList", w.snippetsList)
            }
        }

        saveStoryLines()
        handleStoryLineDeselected()
        Log.i("nuws", "snippet nuwlsit is: ${snippet.nuwList}")
        FireSnippets.add(snippet, context)
        characterList.value = mutableListOf()
        WordConnection.connect(snippet)
        handleWordDeselected()
        handleCharacterDeselected()
    }

    private fun saveStoryLines() {
        for (storyLine in liveStoryLineList.value!!) {
            if(storyLine.selected && !snippet.storyLineList.contains(storyLine.id)) {
                snippet.storyLineList.add(storyLine.id)
                storyLine.snippetList.add(snippet.id)
                FireStoryLines.update(storyLine.id, "snippetList", storyLine.snippetList)
            }
            if(!storyLine.selected) {
                snippet.storyLineList.remove(storyLine.id)
                storyLine.snippetList.remove(snippet.id)
                FireStoryLines.update(storyLine.id, "snippetList", storyLine.snippetList)
            }
        }
        FireSnippets.update(snippet.id, "storyLineList", snippet.storyLineList)

    }


    private fun setObserver() {
        liveWordList.observe(context as LifecycleOwner) {
            val selectedWords = mutableListOf<Word>()
            for(w  in it) {
                if(w.selected && !selectedWords.contains(w)) selectedWords.add(w)
            }
        }

        characterList.observe(context as LifecycleOwner) {
            val selectedChars = mutableListOf<Character>()
            for(c in it) {
                if(c.selected && !selectedChars.contains(c)) selectedChars.add(c)
            }
            charAdapter.submitList(selectedChars)
        }
    }

    private fun setRecycler() {
        charAdapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        b.characterRecycler.adapter = charAdapter
        charAdapter.submitList(snippet.getCharacters())

    }


}