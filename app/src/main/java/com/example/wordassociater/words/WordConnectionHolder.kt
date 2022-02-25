package com.example.wordassociater.words

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.Main
import com.example.wordassociater.databinding.HolderWordConnectionsBinding
import com.example.wordassociater.fire_classes.WordConnection

class WordConnectionHolder(val b : HolderWordConnectionsBinding): RecyclerView.ViewHolder(b.root) {
    private lateinit var wordConnection : WordConnection

    fun onBind(wordConnection: WordConnection) {
        this.wordConnection = wordConnection
        setContent()
    }

    private fun setContent() {
        var theOtherWord = ""
        for(w in wordConnection.wordsList) {
            theOtherWord += Main.getWord(w)?.text + " "
        }
        b.content.text = theOtherWord
    }

}
