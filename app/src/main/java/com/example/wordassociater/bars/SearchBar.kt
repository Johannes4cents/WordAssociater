package com.example.wordassociater.bars

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View.OnKeyListener
import android.widget.LinearLayout
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarSearchBinding
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.Helper
import java.util.*

@SuppressLint("AppCompatCustomView")
class SearchBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarSearchBinding.inflate(LayoutInflater.from(context), this, true)
    val searchWords = MutableLiveData<List<String>>()
    var orMode = true

    init {
        setKeyListener()
        onChangeListener()
    }

    fun setHint(hint: String) {
        b.searchInput.hint = hint
    }

    fun setGravityToCenter() {
        b.searchInput.gravity = Gravity.CENTER
    }

    fun setTextColorToWhite() {
        b.searchInput.setTextColor(b.root.resources.getColor(R.color.white))
        b.searchInput.setHintTextColor(b.root.resources.getColor(R.color.white))
    }

    private fun setKeyListener() {
        b.searchInput.setOnKeyListener(OnKeyListener { v, keyCode, event ->
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
        b.searchInput.doOnTextChanged { text, start, before, count ->
            if(text != null) {
                updateList()
            }
        }
    }

    private fun updateList() {
        var newWords = b.searchInput.text.split("\\s".toRegex()).toMutableList()
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

            if(it.count() == 1 && it[0].length > 2 && foundWords.isEmpty()) {
                val header = Word(id = 0)
                header.isHeader = true
                header.text = it[0].capitalize(Locale.ROOT)
                takeWordsFunc(listOf(header) + foundWords)

            }
            else takeWordsFunc(foundWords)
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