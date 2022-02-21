package com.example.wordassociater.word

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderWordSimpleBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.AdapterType

class WordHolderSimple(val b : HolderWordSimpleBinding): RecyclerView.ViewHolder(b.root) {
    private lateinit var word : Word
    private lateinit var takeWordFunc: (word: Word) -> Unit
    private lateinit var adapterType: AdapterType
    private var rightButtonFunc: ((word: Word) -> Unit)? = null

    fun onBind(word: Word, adapterType: AdapterType, takeWordFunc: (word: Word) -> Unit, rightButtonFunc: ((word: Word) -> Unit) ? = null) {
        this.takeWordFunc = takeWordFunc
        this.word = word
        this.rightButtonFunc = rightButtonFunc
        setContent()
        setClickListener()
    }

    private fun setContent() {
        b.content.text = word.text
        if(word.imgUrl != "") Glide.with(b.root).load(word.imgUrl).into(b.characterPortrait)
        if(adapterType == AdapterType.Popup) b.checkbox.setImageResource(if(word.isPicked) R.drawable.checked_box else R.drawable.unchecked_box)
        else b.checkbox.setImageResource(R.drawable.arrow_right)
        b.typeInitials.text = setTypeInitials(word.type)
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            takeWordFunc(word)
        }

        b.checkbox.setOnClickListener {
            if(rightButtonFunc != null) rightButtonFunc!!(word)
        }
    }

    private fun setTypeInitials(type: Word.Type): String {
        return when(type) {
            Word.Type.Adjective -> "Adj"
            Word.Type.Person -> "Per"
            Word.Type.Place -> "Pla"
            Word.Type.Action -> "Act"
            Word.Type.Object -> "Obj"
            Word.Type.CHARACTER -> "Cha"
            Word.Type.NONE -> "Non"
        }
    }


}