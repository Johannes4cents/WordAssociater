package com.example.wordassociater.wordcat

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
        when (type) {
            WordCatAdapter.Type.List -> setContentList()
            WordCatAdapter.Type.SINGLEPICK -> setContentSinglePick()
            WordCatAdapter.Type.ALLOPTIONS -> setContentAllOptions()
        }
        setClickListener()
    }

    private fun setContentSinglePick() {
        b.checkBox.visibility = View.GONE
        b.deleteBtn.visibility = View.GONE
    }

    private fun setContentAllOptions() {
        b.deleteBtn.setOnClickListener {
            wordCat.delete()
        }

        if(wordCat.type != WordCat.Type.Other) b.deleteBtn.visibility = View.GONE
    }

    private fun setContentList() {
        b.deleteBtn.visibility = View.GONE
    }

    private fun setContent() {
        b.plusSign.setImageResource(wordCat.getBg())
        b.catName.text = wordCat.name
        b.checkBox.setImageResource(if (wordCat.isSelected) R.drawable.checked_box else R.drawable.checkbox_unchecked)
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            onCatClicked(wordCat)
        }
    }

    }
