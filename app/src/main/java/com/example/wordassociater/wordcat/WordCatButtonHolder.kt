package com.example.wordassociater.wordcat

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

class WordCatButtonHolder(
    val b : HolderWordCatBinding,
    val onCatClicked: (wordCat: WordCat) -> Unit, val onHeaderClicked: (() -> Unit)? = null,
): RecyclerView.ViewHolder(b.root) {
    lateinit var wordCat: WordCat

    companion object {
        var selectedWordCat = MutableLiveData<WordCat>()
    }

    fun onBind(wordCat: WordCat) {
        this.wordCat = wordCat
        setContent()
        setClickListener()
        setObserver()
        setFilterColorOnStart()
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
            if(Main.inFragment != Frags.START) b.descriptionText.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
        }
    }

    private fun setFilterColorOnStart() {
        if(Main.inFragment != Frags.START) b.descriptionText.setTextColor(if(DisplayFilter.barColorDark.value!!) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            if(!wordCat.isHeader) {
                selectedWordCat.value = wordCat
                onCatClicked(wordCat)
            }
            else {
                if(onHeaderClicked != null) onHeaderClicked!!()
            }
        }
    }


}

