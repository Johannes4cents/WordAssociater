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

class WordHolder(context: Context, attrs: AttributeSet?, val word: Word): ConstraintLayout(context, attrs) {
    val b = HolderWordBinding.inflate(LayoutInflater.from(context), this, true)
    var char = false


    init {
        setConnectIcon()
        handleImages()
        setClickListener()
        setWord()
        highLightSelected()
    }

    private fun handleImages() {
        when(word.type) {
            Word.Type.Character -> {
                val character = Main.getCharacterByConnectId(word.connectId)
                if(character != null) {
                    b.characterPortrait.visibility = View.VISIBLE
                    if(character.imgUrl != "") Glide.with(context).load(character.imgUrl).into(b.characterPortrait)
                    else b.characterPortrait.setImageResource(word.getImage().getDrawable())
                }
            }
            Word.Type.Item -> {
                val item = Main.getItemByConnectId(word.connectId)
                if(item != null) {
                    b.characterPortrait.visibility = View.VISIBLE
                    if(item.imgUrl != "") Glide.with(context).load(item.imgUrl).into(b.characterPortrait)
                    else b.characterPortrait.setImageResource(word.getImage().getDrawable())
                }
            }
            Word.Type.Location -> {
                val location = Main.getLocationByConnectId(word.connectId)
                if(location != null) {
                    b.characterPortrait.visibility = View.VISIBLE
                    if(location.imgUrl != "") Glide.with(context).load(location.imgUrl).into(b.characterPortrait)
                    else b.characterPortrait.setImageResource(word.getImage().getDrawable())
                }

            }
            Word.Type.Event ->  {
                val event = Main.getEventByConnectId(word.connectId)
                if(event != null) {
                    b.characterPortrait.visibility = View.VISIBLE
                    if(event.imgUrl != "") Glide.with(context).load(event.imgUrl).into(b.characterPortrait)
                    else b.characterPortrait.setImageResource(word.getImage().getDrawable())
                }

            }
            Word.Type.Other ->  {
            }
        }
    }

    private fun highLightSelected() {
        b.selectLinear.setBackgroundColor(if(word.selected) b.selectLinear.resources.getColor(R.color.lightYellow) else b.root.resources.getColor(R.color.snippets))
        b.wordText.setTextColor(if(word.selected) b.root.resources.getColor(R.color.black) else b.root.resources.getColor(R.color.white))
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            word.selected = !word.selected
            selectSnippetPart()
            if(word.selected) {
                WordLinear.selectedWords.add(word)
            }
            else {
                WordLinear.selectedWords.remove(word)
            }
            val index = WordLinear.wordList.indexOf(word)
            WordLinear.wordList.remove(word)
            WordLinear.wordList.add(index, word)
            WordLinear.wordListTrigger.postValue(Unit)
        }
    }

    private fun selectSnippetPart() {
        when(word.type) {
            Word.Type.Character -> {
                val character = Main.getCharacterByConnectId(word.connectId)!!
                NewSnippetBar.newSnippet.takeCharacter(character)
            }
            Word.Type.Item -> {
                val item = Main.getItemByConnectId(word.connectId)!!
                NewSnippetBar.newSnippet.takeItem(item)
            }
            Word.Type.Location -> {
                val location = Main.getLocationByConnectId(word.connectId)!!
                NewSnippetBar.newSnippet.takeLocation(location)
            }
            Word.Type.Event -> {
                val event = Main.getEventByConnectId(word.connectId)!!
                NewSnippetBar.newSnippet.takeEvent(event)
            }
            Word.Type.Other -> {
            }
        }
    }


    private fun setConnectIcon() {
        b.connectIcon.visibility = if(word.wordConnectionsList.count() > 0) View.VISIBLE else View.GONE
    }


    private fun setWord() {
        b.wordText.text = if(word.getFamStrings().isNotEmpty()) getShortenedWord(word.getFamStrings().random(), b.wordText) else "word '${word.text}' has no fams | ${word.id}"
        b.catIcon.setImageResource(if(word.cats.count() > 1) Main.getWordCat(word.cats[1])!!.getBg() else Main.getWordCat(word.cats[0])!!.getBg())
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

