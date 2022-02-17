package com.example.wordassociater.popups

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderWordBinding
import com.example.wordassociater.databinding.HolderWordPopupBinding
import com.example.wordassociater.start_fragment.StartFragment
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.start_fragment.getLineCount
import com.example.wordassociater.start_fragment.getShortenedWord

class WHolder(val b: HolderWordPopupBinding, val fromWordList: Boolean = false): RecyclerView.ViewHolder(b.root) {
    lateinit var word: Word
    lateinit var onClickFunc: (word: Word) -> Unit
    lateinit var btnNewWordFunc: (word:Word) -> Unit
    fun onBind(word: Word, onClickFunc: (word: Word) -> Unit, btnNewWordFunc: (word:Word) -> Unit) {
        this.btnNewWordFunc = btnNewWordFunc
        this.onClickFunc = onClickFunc
        this.word = word
        setContent()
        setClickListener()
    }

    private fun setContent() {
        if(!fromWordList) b.btnNewWord.setImageResource(R.drawable.icon_red_x)
        else b.btnNewWord.setImageResource(R.drawable.arrow_right)
        b.wordBgImage.setImageResource(getWordBg(word.type))
        setWord()
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            onClickFunc(word)
        }

        b.btnNewWord.setOnClickListener {
            btnNewWordFunc(word)
        }

    }

    private fun setWord() {
        b.wordText.text = getShortenedWord(word.text, b.wordText)
        getLineCount(word)
        if(word.lineCount > 1) b.wordText.textSize = 12f
    }


    private fun getWordBg(type: Word.Type): Int {
        return when(type) {
            Word.Type.Adjective -> R.drawable.word_bg_adjective
            Word.Type.Person -> R.drawable.word_bg_person
            Word.Type.Place -> R.drawable.word_bg_place
            Word.Type.Action -> R.drawable.word_bg_action
            Word.Type.Object -> R.drawable.word_bg_objects
            Word.Type.CHARACTER -> R.drawable.word_bg_hero
            Word.Type.NONE -> R.drawable.word_bg_objects
        }
    }
}