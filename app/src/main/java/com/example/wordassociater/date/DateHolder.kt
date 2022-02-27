package com.example.wordassociater.date

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.wordassociater.databinding.DateBinding
import com.example.wordassociater.utils.Date

class DateHolder(context: Context, attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {
    val b = DateBinding.inflate(LayoutInflater.from(context), this, true)

    init {

    }

    fun setClickListener() {

    }

    fun onDateSelected(date: Date) {

    }
}