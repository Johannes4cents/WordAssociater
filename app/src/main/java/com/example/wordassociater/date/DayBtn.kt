package com.example.wordassociater.date

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BtnDayBinding

class DayBtn(context: Context, attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {
    val b = BtnDayBinding.inflate(LayoutInflater.from(context), this, true)
    val day: String

    init {
        context.theme.obtainStyledAttributes(
                attributeSet,
                R.styleable.DayBtn,
                0, 0).apply {
            try {
                day = getString(R.styleable.DayBtn_dayNumber).toString()
            } finally {
                recycle()
            }
        }

    }
}