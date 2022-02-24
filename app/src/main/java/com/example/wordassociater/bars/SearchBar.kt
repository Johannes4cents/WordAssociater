package com.example.wordassociater.bars

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View.OnKeyListener
import android.widget.LinearLayout
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.databinding.BarSearchBinding
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.Helper

@SuppressLint("AppCompatCustomView")
class SearchBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarSearchBinding.inflate(LayoutInflater.from(context), this, true)
    val searchWords = MutableLiveData<List<String>>()
    var orMode = true

    init {
        setKeyListener()
        onChangeListener()
    }


    private fun setKeyListener() {
        b.searchStrainsInput.setOnKeyListener(OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                updateList()
                return@OnKeyListener true
            }
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                Helper.toast("list is empty", context)
                updateList()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun onChangeListener() {
        b.searchStrainsInput.doOnTextChanged { text, start, before, count ->
            if(text != null) {
                updateList()
            }
        }
    }

    private fun updateList() {
        var newWords = b.searchStrainsInput.text.split("\\s".toRegex()).toMutableList()
        var strippedWords = mutableListOf<String>()
        for(w in newWords) {
            if(w.isNotEmpty()) strippedWords.add(Helper.stripWord(w))
        }
        searchWords.value = strippedWords
    }

    fun getWords( takeWordsFunc: (wordsList: List<Word>) -> Unit) {
        val allWords = Main.wordsList.value!!.toMutableList()
        searchWords.observe(context as LifecycleOwner) {
            val foundWords = mutableListOf<Word>()
            for(word in allWords) {
                for(string in it) {
                    if(Helper.stripWord(word.text).startsWith(string)) foundWords.add(word)
                }
            }
            takeWordsFunc(foundWords)
        }
    }

    fun getStrains( takeStrainsFunc: (strainsList: List<Strain>) -> Unit ) {
        val allStrains = Main.strainsList.value!!.toMutableList()
        searchWords.observe(context as LifecycleOwner) {
            val foundStrains = mutableListOf<Strain>()
            for(strain in allStrains) {
                val strainContentWords = Helper.contentToWordList(strain.content) + Helper.contentToWordList(strain.header)
                for(string in it) {
                    // Search Content
                    for(wordString in strainContentWords) {
                        if(wordString.startsWith(string) && !foundStrains.contains(strain)) {
                            foundStrains.add(strain)
                        }
                    }
                }
            }
            takeStrainsFunc(foundStrains)
        }
    }

    fun getSnippets( takeSnippetsFunc: (snippetList: List<Snippet>) -> Unit ) {
        val allSnippets = Main.snippetList.value!!.toMutableList()
        searchWords.observe(context as LifecycleOwner) {
            val foundSnippets = mutableListOf<Snippet>()
            for(snippet in allSnippets) {
                val strainContentWords = Helper.contentToWordList(snippet.content)
                for(string in it) {
                    // Search Content
                    for(wordString in strainContentWords) {
                        if(wordString.startsWith(string) && !foundSnippets.contains(snippet)) {
                            foundSnippets.add(snippet)
                        }
                    }
                }
            }
            takeSnippetsFunc(foundSnippets)
        }
    }

}