package com.example.wordassociater.words

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.Main
import com.example.wordassociater.databinding.HolderWordConnectionsBinding
import com.example.wordassociater.utils.WordConnection

class WordConnectionHolder(val b : HolderWordConnectionsBinding): RecyclerView.ViewHolder(b.root) {
    private lateinit var wordConnection : WordConnection

    fun onBind(wordConnection: WordConnection) {
        this.wordConnection = wordConnection
        setContent()
    }

    private fun setContent() {
        b.content.text = Main.getWord(wordConnection.word)?.text
        b.connectionAmount.text = wordConnection.connected.toString()
    }

}
