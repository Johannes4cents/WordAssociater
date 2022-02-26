package com.example.wordassociater.words

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderWordPreviewBinding
import com.example.wordassociater.fire_classes.Word

class WordHolderPreview(val b : HolderWordPreviewBinding): RecyclerView.ViewHolder(b.root) {
    private lateinit var word : Word


    fun onBind(word: Word) {
        this.word = word
        setContent()
    }

    private fun setContent() {

        val firstCat = word.getCatsList()[0]
        b.content.text = word.text
        b.typeInitials.text = firstCat.name.take(3)
        b.typeInitials.setBackgroundColor(b.root.context.resources.getColor(firstCat.getColor()))
    }


}