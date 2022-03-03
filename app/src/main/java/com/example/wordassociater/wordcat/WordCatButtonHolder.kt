package com.example.wordassociater.wordcat

import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderWordCatBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.firestore.FireWordCats
import com.example.wordassociater.popups.popWordCatAllOptions

class WordCatButtonHolder(val b : HolderWordCatBinding, val onCatClicked: (wordCat: WordCat) -> Unit): RecyclerView.ViewHolder(b.root) {
    lateinit var wordCat: WordCat

    companion object {
        var selectedWordCat = MutableLiveData<WordCat>()
    }

    fun onBind(wordCat: WordCat) {
        this.wordCat = wordCat
        Log.i("wordCatProb", "wordcat is ${wordCat}")
        setContent()
        setClickListener()
        setObserver()
    }

    private fun setContent() {
        if(wordCat.isHeader) {
            b.btnImage.setImageResource(R.drawable.wordcat_plus_header)
            b.descriptionText.visibility = View.GONE
        }
        else {
            b.descriptionText.text = wordCat.name
            b.btnImage.setImageResource(wordCat.getBg())
            b.descriptionText.visibility = View.VISIBLE
            b.descriptionText.requestLayout()


        }
    }

    private fun setObserver() {
        selectedWordCat.observe(b.root.context as LifecycleOwner) {
            if(Main.inFragment != Frags.START) b.btnImage.setImageResource(if(selectedWordCat.value != wordCat && !wordCat.isHeader) wordCat.getBg() else R.drawable.wordcat_bg_selected)
        }

        DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
            b.descriptionText.setTextColor(if(it) b.root.context.resources.getColor(R.color.black) else b.root.context.resources.getColor(R.color.white))
        }
    }

    // displayedWordCatSelection
    private val selectedWordCats = MutableLiveData<List<WordCat>>()

    private fun setClickListener() {
        b.root.setOnClickListener {
            if(!wordCat.isHeader) {
                selectedWordCat.value = wordCat
                onCatClicked(wordCat)
            }
            else {
                setWordCatsOnOpen()
                popWordCatAllOptions(b.root, selectedWordCats, ::onWordCatSelected)
            }
        }
    }
    private fun onWordCatSelected(wordCat: WordCat) {
        val newActiveWordCats = Main.activeWordCats.value!!.toMutableList()
        val newWordCats = selectedWordCats.value!!.toMutableList()
        for(wc in newWordCats) {
            if(wc.id == wordCat.id) {
                Log.i("wordCatProb", "word found wc is ${wc.name}")
                wc.isSelected = !wc.isSelected
                wc.active = !wc.active
                FireWordCats.update(wc.id, "active", wc.active)
                if(wc.active) newActiveWordCats.add(wc)
                else
                    newActiveWordCats.remove(wc)
            }
        }
        Main.wordCatsList.value = newWordCats
        selectedWordCats.value = newWordCats
    }

    private fun setWordCatsOnOpen() {
        selectedWordCats.value = Main.wordCatsList.value!!.toMutableList()
        for(wc in selectedWordCats.value!!) {
            wc.isSelected = Main.activeWordCats.value!!.contains(wc)
        }
    }

}

