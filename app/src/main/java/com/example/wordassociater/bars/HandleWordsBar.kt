package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.BarWordsButtonsBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.words.WordLinear
import com.google.android.gms.common.util.CollectionUtils

class HandleWordsBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    companion object {
        var shuffleBackupWords = mutableListOf<Word.Type>()

        fun getWord(type: Word.Type): Word {
            var randomWord : Word? = null
            while(randomWord == null || WordLinear.wordList.contains(randomWord)) {
                randomWord = when(type) {
                    Word.Type.Adjective -> WordLinear.adjectivesList.random()
                    Word.Type.Person -> WordLinear.personsList.random()
                    Word.Type.CHARACTER -> WordLinear.characterList.random()
                    Word.Type.Place -> WordLinear.placesList.random()
                    Word.Type.Action -> WordLinear.actionsList.random()
                    Word.Type.Object -> WordLinear.objectsList.random()
                    Word.Type.NONE -> WordLinear.objectsList.random()
                }
            }
            return randomWord
        }
    }

    var wordAmount = 6
    val b = BarWordsButtonsBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setClickListener()
        setUpSpinner()
    }

    private fun deleteSelectedWords(confirmed: Boolean): Unit {
        if(confirmed) {
            for(word in WordLinear.selectedWords) {
                WordLinear.wordList.remove(word)
                FireWords.delete(word)
            }
            WordLinear.selectedWords.clear()
            WordLinear.wordListTriger.value = Unit
        }
    }

    private fun setClickListener() {
        b.deleteWordsButton.setOnClickListener {
            Pop(context).confirmationPopUp(b.deleteWordsButton, ::deleteSelectedWords)
        }

        b.clearAllBtn.setOnClickListener {
            WordLinear.wordList = WordLinear.selectedWords.toMutableList()
            handleCharacterRemoval()
            WordLinear.wordListTriger.postValue(Unit)
        }

        b.shuffleBtn.setOnClickListener {
            WordLinear.wordList = WordLinear.selectedWords.toMutableList()
            handleCharacterRemoval()
            for(wordType : Word.Type in shuffleBackupWords) {
                WordLinear.wordList.add(getWord(wordType))
            }
            WordLinear.wordListTriger.postValue(Unit)
        }

        b.rollDiceBtn.setOnClickListener {
            if(WordLinear.placesList.isNotEmpty()) {
                WordLinear.wordList = WordLinear.selectedWords.toMutableList()
                handleCharacterRemoval()
                val randomCats = listOf<Word.Type>(Word.Type.CHARACTER, Word.Type.Action, Word.Type.Object, Word.Type.Person, Word.Type.Adjective, Word.Type.Place, Word.Type.Object, Word.Type.Action)
                for(i in 1..wordAmount) {
                    WordLinear.wordList.add(getWord(randomCats.random()))
                }
                WordLinear.wordListTriger.postValue(Unit)
            }
        }
    }

    private fun handleCharacterRemoval() {
        for(char in CharacterAdapter.selectedNameChars) {
            CharacterAdapter.selectedCharacterList.remove(char)
        }
        CharacterAdapter.selectedNameChars.clear()
    }

    private fun setUpSpinner() {
        val optionList = CollectionUtils.listOf<Int>(1,2,4,6,8,10,12,16)
        val adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, optionList)
        b.diceSpinner.adapter = adapter
        b.diceSpinner.setSelection(2)

        b.diceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                wordAmount = optionList[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

}