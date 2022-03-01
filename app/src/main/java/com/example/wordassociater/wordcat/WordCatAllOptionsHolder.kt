package com.example.wordassociater.wordcat

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderWordCatAllOptionsBinding
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.popups.Pop

class WordCatAllOptionsHolder(
        val type: WordCatAdapter.Type,
        val b : HolderWordCatAllOptionsBinding,
        val onCatClicked: (wordCat: WordCat) -> Unit)
    : RecyclerView.ViewHolder(b.root) {
    lateinit var wordCat: WordCat

    fun onBind(wordCat: WordCat) {
        this.wordCat = wordCat
        setContent()
        setClickListener()
    }

    private fun setContent() {
        b.plusSign.setImageResource(wordCat.getBg())
        b.catName.text = wordCat.name
        b.checkBox.setImageResource(if(wordCat.isSelected) R.drawable.checked_box else R.drawable.checkbox_unchecked)

    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            onCatClicked(wordCat)
        }

        b.deleteBtn.setOnClickListener {
            Pop(b.root.context).confirmationPopUp(b.deleteBtn, ::onConfirmationDelete)
        }
    }

    private fun onConfirmationDelete(confirmation: Boolean) {
        if(confirmation)
            wordCat.delete()
    }

}