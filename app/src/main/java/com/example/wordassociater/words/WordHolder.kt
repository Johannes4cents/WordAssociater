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
import com.example.wordassociater.bars.NewSnippetBar
import com.example.wordassociater.databinding.HolderWordBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.ListHelper

class WordHolder(context: Context, attrs: AttributeSet?, val word: Word? = null ): ConstraintLayout(context, attrs) {
    val b = HolderWordBinding.inflate(LayoutInflater.from(context), this, true)
    var char = false


    init {
        setConnectIcon()
        checkIfCharacter()
        setClickListener()
        setWord()
        setPortrait()
        highLightSelected()
    }

    private fun checkIfCharacter() {
        for(cat in word!!.cats) {
            if(Main.getWordCat(cat)?.name == "Character") {
                char = true
                break
            }
        }
    }

    private fun highLightSelected() {
        b.selectLinear.setBackgroundColor(if(word!!.selected) b.selectLinear.resources.getColor(R.color.lightYellow) else b.root.resources.getColor(R.color.snippets))
        b.wordText.setTextColor(if(word.selected) b.root.resources.getColor(R.color.black) else b.root.resources.getColor(R.color.white))
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            word?.selected = !word?.selected!!
            if(word.selected) {
                WordLinear.selectedWords.add(word)
                selectCharacter()

            }
            else {
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
        if(char) {
            val character = Main.characterList.value?.find { c ->
                c.connectId == word!!.connectId
            }
            if(character != null) {
                val listAlreadyThere = ListHelper.checkIfCharacterSelected(NewSnippetBar.selectedCharacterList.value!!)
                val charList = if(!listAlreadyThere) Main.characterList.value!!.toMutableList() else NewSnippetBar.selectedCharacterList.value!!.toMutableList()
                for(c in charList) {
                    if(c.id == character.id)c.selected = !c.selected
                }
                NewSnippetBar.selectedCharacterList.value = charList
            }
        }
    }

    private fun deselectCharacter() {
        if(char) {
            val character = NewSnippetBar.selectedCharacterList.value?.find { c ->
                c.connectId == word!!.connectId
            }
            if(character != null) {
                val listAlreadyThere = ListHelper.checkIfCharacterSelected(NewSnippetBar.selectedCharacterList.value!!)
                val charList = if(!listAlreadyThere) Main.characterList.value!!.toMutableList() else NewSnippetBar.selectedCharacterList.value!!.toMutableList()
                for(c in charList) {
                    if(c.id == character.id)c.selected = !c.selected
                }
                NewSnippetBar.selectedCharacterList.value = charList
            }
        }

    }

    private fun setConnectIcon() {
        b.connectIcon.visibility = if(word!!.wordConnectionsList.count() > 0) View.VISIBLE else View.GONE
    }


    private fun setWord() {
        b.wordText.text = getShortenedWord(word!!.getFamStrings().random(), b.wordText)
        b.catIcon.setImageResource(Main.getWordCat(word.cats[0])!!.getBg())
        
    }

    private fun setPortrait() {
        if(char) {
            val character = Main.characterList.value?.find { char ->
                char.name.equals(word!!.text, ignoreCase = true)
            }
            if(character != null) {
                b.characterPortrait.visibility = View.VISIBLE
                Glide.with(context).load(character.imgUrl).into(b.characterPortrait)
            }
        }
    }


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

