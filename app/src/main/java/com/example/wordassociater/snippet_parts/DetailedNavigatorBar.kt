package com.example.wordassociater.snippet_parts

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.wordassociater.databinding.BarDetailedNavigatorBinding

class DetailedNavigatorBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarDetailedNavigatorBinding.inflate(LayoutInflater.from(context), this, true)

}