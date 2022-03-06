package com.example.wordassociater.words

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HeaderSearchWordBinding
import com.example.wordassociater.fire_classes.Word

class SearchWordsHeader(val b: HeaderSearchWordBinding, val onAddWord: ((text: String) -> Unit)?): RecyclerView.ViewHolder(b.root) {

    fun onBind(wordString: Word) {
        Log.i("searchHeader", "inside onBind of header")
        b.root.setOnClickListener {
            onAddWord!!(wordString.text)
        }

        b.newWordText.text = wordString.text
    }
}