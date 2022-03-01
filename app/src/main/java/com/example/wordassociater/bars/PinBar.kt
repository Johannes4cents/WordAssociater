package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.wordassociater.databinding.BarPinBinding

class PinBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarPinBinding.inflate(LayoutInflater.from(context), this, true)

    init {

    }
}