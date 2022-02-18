package com.example.wordassociater.start_fragment

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.bars.HandleWordsBar
import com.example.wordassociater.databinding.WordLinearBinding
import com.example.wordassociater.fire_classes.Word

class WordLinear(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    companion object {
        var leftSelected = true
        val allWords = mutableListOf<Word>()
        var wordList = mutableListOf<Word>()
        val wordListTriger = MutableLiveData<Unit>()
        val selectedWords = mutableListOf<Word>()
        val adjectivesList = mutableListOf<Word>()
        val actionsList = mutableListOf<Word>()
        val objectsList = mutableListOf<Word>()
        val personsList = mutableListOf<Word>()
        val placesList = mutableListOf<Word>()
        val characterList = mutableListOf<Word>()

        fun deselectWords() {
            for(w in WordLinear.selectedWords) {
                w.selected = false
                val index = WordLinear.wordList.indexOf(w)
                WordLinear.wordList.remove(w)
                WordLinear.wordList.add(index, w)
            }
            WordLinear.selectedWords.clear()
            WordLinear.wordListTriger.value = Unit
        }

        fun getWordList(type: Word.Type): MutableList<Word> {
            return when(type) {
                Word.Type.Adjective -> WordLinear.adjectivesList
                Word.Type.Person -> WordLinear.personsList
                Word.Type.Place -> WordLinear.placesList
                Word.Type.Action -> WordLinear.actionsList
                Word.Type.Object -> WordLinear.objectsList
                Word.Type.CHARACTER -> WordLinear.characterList
                Word.Type.NONE -> WordLinear.objectsList
            }
        }
    }
    val b = WordLinearBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setObserver()
    }

    private fun setObserver() {
        wordListTriger.observe(context as LifecycleOwner) {
            b.leftLinear.removeAllViews()
            b.rightLinear.removeAllViews()
            HandleWordsBar.shuffleBackupWords.clear()
            for(w : Word in wordList) {
                leftSelected = b.leftLinear.childCount <= b.rightLinear.childCount
                val wordHolder = WordHolder(context, null, word = w)
                if(!selectedWords.contains(w)) HandleWordsBar.shuffleBackupWords.add(w.type)
                if(leftSelected) {
                    b.leftLinear.addView(wordHolder)
                }
                else {
                    b.rightLinear.addView(wordHolder)
                }
            }
        }
    }

}