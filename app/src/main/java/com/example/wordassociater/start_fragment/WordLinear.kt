package com.example.wordassociater.start_fragment

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.bars.HandleWordsBar
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.WordLinearBinding

class WordLinear(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    companion object {
        var leftSelected = true

        var allword = mutableListOf<Word>()
        var wordList = mutableListOf<Word>()
        val wordListTriger = MutableLiveData<Unit>()
        val selectedWords = mutableListOf<Word>()
        val adjectivesList = mutableListOf<Word>()
        val actionsList = mutableListOf<Word>()
        val objectsList = mutableListOf<Word>()
        val personsList = mutableListOf<Word>()
        val placesList = mutableListOf<Word>()
        val characterList = mutableListOf<Word>()
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