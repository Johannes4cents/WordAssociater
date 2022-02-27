package com.example.wordassociater.wordcat

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderWordCatBinding
import com.example.wordassociater.fire_classes.WordCat

class WordCatButtonHolder(val b : HolderWordCatBinding, val onCatClicked: (wordCat: WordCat) -> Unit): RecyclerView.ViewHolder(b.root) {
    lateinit var wordCat: WordCat

    companion object {
        var selectedWordCat = MutableLiveData<WordCat>()
    }

    fun onBind(wordCat: WordCat) {
        this.wordCat = wordCat
        setContent()
        setClickListener()
        setObserver()
        Log.i("wordCat", "isHeader? ${wordCat.isHeader}")
    }

    private fun setContent() {
        if(!wordCat.isHeader) {
            b.descriptionText.text = wordCat.name
            b.btnImage.setImageResource(wordCat.getBg())
        }
        else {
            b.btnImage.setImageResource(R.drawable.wordcat_plus_header)
            b.descriptionText.text = ""
        }
    }

    private fun setObserver() {
        selectedWordCat.observe(b.root.context as LifecycleOwner) {
            b.btnImage.setImageResource(if(selectedWordCat.value != wordCat && !wordCat.isHeader) wordCat.getBg() else R.drawable.wordcat_bg_selected)
        }
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            if(!wordCat.isHeader) {
                selectedWordCat.value = wordCat
                onCatClicked(wordCat)
            }
        }
    }

}