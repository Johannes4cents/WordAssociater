package com.example.wordassociater.date

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BtnMonthBinding

class MonthBtn(context: Context, attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {
    val b = BtnMonthBinding.inflate(LayoutInflater.from(context), this, true)
    var month: String

    init {
        context.theme.obtainStyledAttributes(
                attributeSet,
                R.styleable.MonthBtn,
                0, 0).apply {

            try {
                month = getString(R.styleable.MonthBtn_monthName).toString()
            } finally {
                recycle()
            }
        }

    }
}