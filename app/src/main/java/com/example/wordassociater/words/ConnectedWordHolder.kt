package com.example.wordassociater.words

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderConnectedWordBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.ConnectedWord

class ConnectedWordHolder(
        val b: HolderConnectedWordBinding,
        val onWordSelected: (word: Word) -> Unit)
    : RecyclerView.ViewHolder(b.root) {

    lateinit var connectedWord: ConnectedWord
    fun onBind(connectedWord: ConnectedWord) {
        this.connectedWord = connectedWord
        setClickListener()
        setContent()
    }

    private fun setContent() {
        b.wordName.text = connectedWord.word.text
        b.fieldAmountConnected.text = connectedWord.amount.toString()
        b.typeInitials.setBackgroundColor(b.root.context.resources.getColor(connectedWord.word.getColor(connectedWord.word.type)))
        b.typeInitials.text = connectedWord.word.getTypeInitials()
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            onWordSelected(connectedWord.word)
        }
    }
}