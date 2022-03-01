package com.example.wordassociater.words

import android.util.Log
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
        Log.i("connectedW", "ConnectedWordHolderOnBind callen connectedWord is $connectedWord")
        this.connectedWord = connectedWord
        setClickListener()
        setContent()
    }

    private fun setContent() {
        val firstCat = connectedWord.word.getCatsList()[0]
        b.wordName.text = connectedWord.word.text
        b.fieldAmountConnected.text = connectedWord.amount.toString()
        b.typeInitials.text = firstCat.name.take(3)
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            onWordSelected(connectedWord.word)
        }
    }
}