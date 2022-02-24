package com.example.wordassociater.words

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.bars.HandleWordsBar
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.HolderWordBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.Helper

class WordHolder(context: Context, attrs: AttributeSet?, val word: Word? = null ): ConstraintLayout(context, attrs) {
    val b = HolderWordBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setClickListener()
        setWord()
        setPortrait()
    }

    private fun setClickListener() {
        b.btnNewWord.setOnClickListener {
            getNewWord()
        }

        b.root.setOnClickListener {
            word?.selected = !word?.selected!!
            if(word.selected) {
                WordLinear.selectedWords.add(word)
                selectCharacter()
                HandleWordsBar.shuffleBackupWords.remove(word.type)

            }
            else {
                if(WordLinear.selectedWords.contains(word)) {
                    HandleWordsBar.shuffleBackupWords.add(word.type)
                }
                deselectCharacter()
                WordLinear.selectedWords.remove(word)
            }
            val index = WordLinear.wordList.indexOf(word)
            WordLinear.wordList.remove(word)
            WordLinear.wordList.add(index, word)
            WordLinear.wordListTrigger.postValue(Unit)
        }
    }

    private fun selectCharacter() {
        if(word?.type == Word.Type.CHARACTER) {
            var character = Main.characterList.value?.find { char ->
                char.connectId == word.connectId
            }
            if(character != null) {
                CharacterAdapter.selectedCharacterList.add(character)
                CharacterAdapter.selectedNameChars.add(character)
                CharacterAdapter.characterListTrigger.value = Unit
            }
        }
    }

    private fun deselectCharacter() {
        if(word?.type == Word.Type.CHARACTER) {
            var character = Main.characterList.value?.find { char ->
                char.connectId == word.connectId
            }
            if(character != null) {
                CharacterAdapter.selectedCharacterList.remove(character)
                CharacterAdapter.selectedNameChars.remove(character)
                CharacterAdapter.characterListTrigger.value = Unit
            }
        }

    }


    private fun getNewWord() {
        val index = WordLinear.wordList.indexOf(word)
        WordLinear.wordList.remove(word)
        HandleWordsBar.getWord(word?.type!!)?.let { WordLinear.wordList.add(index, it) }
        WordLinear.wordListTrigger.postValue(Unit)
    }

    private fun setWord() {
        b.wordText.text = getShortenedWord(word?.text!!, b.wordText)
        if(word.selected) {
            b.root.setBackgroundResource(R.drawable.word_bg_selected)
        }
        else {
            b.root.setBackgroundResource(Helper.getWordBg(word.type))
        }

        getLineCount(word)
        if(word.lineCount > 1) b.wordText.textSize = 12f
    }

    private fun setPortrait() {
        if(word!!.type == Word.Type.CHARACTER) {
            val character = Main.characterList.value?.find { char ->
                char.name.equals(word.text, ignoreCase = true)
            }
            if(character != null) {
                b.characterPortrait.visibility = View.VISIBLE
                Glide.with(context).load(character.imgUrl).into(b.characterPortrait)
            }
        }
    }


}



fun getLineCount(word: Word) {
    if((14..100).contains(word.text.length)) word.lineCount = 2
}

fun getShortenedWord(word: String, textView: TextView): String {
    val singleWorldList = word.split("\\s+".toRegex())
    var shortened = ""
    var firstWord = true
    for (word: String in singleWorldList) {
        if(firstWord) {
            shortened += "$word "
            firstWord = false
            if(word.length > 13) textView.textSize = 12F
            continue
        }
        shortened += if((shortened + word).length < 15) {
            "$word "
        } else {
            "\n" + word
        }
    }

    return shortened
}

