package com.example.wordassociater.snippet_parts

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.wordassociater.databinding.DetailsSnippetPartBinding
import com.example.wordassociater.fire_classes.SnippetPart

class SnippetPartDetails(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = DetailsSnippetPartBinding.inflate(LayoutInflater.from(context), this, true)

    lateinit var snippetPart: SnippetPart

    fun initDetails(part: SnippetPart) {
        snippetPart = part

    }

    private fun setContent() {

    }


}