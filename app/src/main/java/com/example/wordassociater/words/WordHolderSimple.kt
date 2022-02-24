package com.example.wordassociater.words

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
    private var firstSet = true

    fun onBind(word: Word, adapterType: AdapterType, takeWordFunc: (word: Word) -> Unit, rightButtonFunc: ((word: Word) -> Unit) ? = null) {
        this.takeWordFunc = takeWordFunc
        this.word = word
        this.rightButtonFunc = rightButtonFunc
        this.adapterType = adapterType
        setContent()
        setClickListener()
    }

    private fun setContent() {
        b.content.text = word.text
        if(word.imgUrl != "") Glide.with(b.root).load(word.imgUrl).into(b.characterPortrait)
        if(adapterType == AdapterType.Popup) b.checkbox.setImageResource(if(word.selected) R.drawable.checked_box else R.drawable.unchecked_box)
        else {
            if(firstSet) {
                b.checkbox.layoutParams.width = b.checkbox.layoutParams.width * 2
                b.checkbox.layoutParams.height = b.checkbox.layoutParams.height * 2
                b.checkbox.requestLayout()
                b.checkbox.setImageResource(R.drawable.arrow_right)
                firstSet = false
            }

        }
        b.typeInitials.text = setTypeInitials(word.type)
        b.typeInitials.setBackgroundColor(b.root.context.resources.getColor(word.getColor(word.type)))
        b.usedOrConnectionsField.text = word.used.toString()
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            b.checkbox.setImageResource(if(!word.selected) R.drawable.checked_box else R.drawable.unchecked_box)
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