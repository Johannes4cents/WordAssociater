package com.example.wordassociater.wordcat

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderWordCatBinding
import com.example.wordassociater.fire_classes.WordCat

class WordCatButtonHolder(val b : HolderWordCatBinding, val onCatClicked: (wordCat: WordCat) -> Unit): RecyclerView.ViewHolder(b.root) {
    lateinit var wordCat: WordCat

    fun onBind(wordCat: WordCat) {
        this.wordCat = wordCat
        setContent()
        setClickListener()
        Log.i("wordCat", "isHeader? ${wordCat.isHeader}")
    }

    private fun setContent() {
        if(!wordCat.isHeader) {
            b.btnImage.setImageResource(wordCat.getPlusSign())
            b.descriptionText.text = wordCat.name
        }
        else {
            b.btnImage.setImageResource(R.drawable.wordcat_plus_header)
            b.descriptionText.text = ""
        }

    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            if(!wordCat.isHeader) onCatClicked(wordCat)
        }
    }

}