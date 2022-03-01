package com.example.wordassociater.wordcat

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderWordCatListBinding
import com.example.wordassociater.fire_classes.WordCat

class WordCatListHolder(
        val type: WordCatAdapter.Type,
        val b : HolderWordCatListBinding,
        val onCatClicked: (wordCat: WordCat) -> Unit)
    : RecyclerView.ViewHolder(b.root) {
    lateinit var wordCat: WordCat

    fun onBind(wordCat: WordCat) {
        this.wordCat = wordCat
        setContent()
        setClickListener()
        Log.i("wordCat", "isHeader? ${wordCat.isHeader}")
    }

    private fun setContent() {
        b.plusSign.setImageResource(wordCat.getBg())
        b.catName.text = wordCat.name
        b.checkBox.setImageResource(if(wordCat.isSelected) R.drawable.checked_box else R.drawable.checkbox_unchecked)

        if(type == WordCatAdapter.Type.SINGLEPICK) b.checkBox.visibility = View.GONE

    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            if(!wordCat.isHeader) onCatClicked(wordCat)
        }
    }

}