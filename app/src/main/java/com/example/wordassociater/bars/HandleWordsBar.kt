package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.BarWordsButtonsBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.words.WordLinear

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
    }

    private fun setClickListener() {

        b.btnClearAll.setOnClickListener {
            WordLinear.wordList = WordLinear.selectedWords.toMutableList()
            handleCharacterRemoval()
            WordLinear.wordListTriger.postValue(Unit)
        }

        b.btnSpheres.setOnClickListener {
            WordLinear.wordList = WordLinear.selectedWords.toMutableList()
            handleCharacterRemoval()
            for(wordType : Word.Type in shuffleBackupWords) {
                WordLinear.wordList.add(getWord(wordType))
            }
            WordLinear.wordListTriger.postValue(Unit)
        }

        b.btnRollDice.setOnClickListener {
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

}