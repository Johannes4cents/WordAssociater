package com.example.wordassociater.wordcat

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.wordassociater.databinding.SelectorWordCatBinding
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.popups.popSingleSelectWordCat

class WordCatSelector(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = SelectorWordCatBinding.inflate(LayoutInflater.from(context), this, true)
    lateinit var onCatSelected: (wordCat: WordCat) -> Unit

    fun setCat(onCatSelected : (wordCat: WordCat) -> Unit) {
        this.onCatSelected = onCatSelected
        b.catName.text = "Adjective"
        setClickListener()
    }

    private fun setName(wordCat: WordCat) {
        b.catName.text = wordCat.name
        b.catIcon.setImageResource(wordCat.getBg())
        onCatSelected(wordCat)
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            popSingleSelectWordCat(
                    b.root, ::setName
            )
        }
    }

}