package com.example.wordassociater.words

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HeaderSearchWordBinding
import com.example.wordassociater.fire_classes.Word

class SearchWordsHeader(val b: HeaderSearchWordBinding, private val onAddWord: ((text: String) -> Unit)?): RecyclerView.ViewHolder(b.root) {

    fun onBind(wordString: Word) {

        b.root.setOnClickListener {
            onAddWord!!(wordString.text)
        }

        b.newWordText.text = wordString.text
    }
}