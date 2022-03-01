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
        b.content.text = word.text
    }


}