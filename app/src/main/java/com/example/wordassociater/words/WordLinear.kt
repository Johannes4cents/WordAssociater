package com.example.wordassociater.words

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.WordLinearBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.utils.Helper

class WordLinear(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    companion object {
        var leftSelected = true
        var wordList = mutableListOf<Word>()
        val wordListTrigger = MutableLiveData<Unit>()
        val selectedWords = mutableListOf<Word>()
        val selectedWordsLive = MutableLiveData<List<Word>>()

        fun deselectWords() {
            for(w in selectedWords) {
                w.selected = false
                val index = wordList.indexOf(w)
                wordList.remove(w)
                wordList.add(index, w)
            }
            selectedWords.clear()
            wordListTrigger.value = Unit
        }

        fun getWord(wordCat: WordCat): Word? {
            var tries = 0
            var randomWord : Word? = null
            while(tries < 200 && (
                            randomWord == null || wordList.contains(randomWord) || !Helper.checkIfWordInRightSphere(randomWord))) {
                val catWords = wordCat.getWords()
                if(catWords.isNotEmpty()) randomWord = catWords.random()
                //Log.i("rollProb", "in getWords during 200 tries - randomWord is ${randomWord?.text}")
                tries++
            }
            if(randomWord != null && !Helper.checkIfWordInRightSphere(randomWord)) randomWord = null
            //Log.i("rollProb", "in getWords after spheres Check - randomWord is ${randomWord?.text}")
            if(wordList.contains(randomWord)) randomWord = null
            //Log.i("rollProb", "in getWords after wordlist.contains check - randomWord is ${randomWord?.text}")
            return randomWord
        }

    }
    val b = WordLinearBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setObserver()
    }

    private fun setObserver() {
        wordListTrigger.observe(context as LifecycleOwner) {
            b.leftLinear.removeAllViews()
            b.rightLinear.removeAllViews()
            for(w : Word in wordList) {
                leftSelected = b.leftLinear.childCount <= b.rightLinear.childCount
                val wordHolder = WordHolder(context, null, word = w)
                if(leftSelected) {
                    b.leftLinear.addView(wordHolder)
                }
                else {
                    b.rightLinear.addView(wordHolder)
                }
            }
            selectedWordsLive.value = selectedWords
        }
    }

}