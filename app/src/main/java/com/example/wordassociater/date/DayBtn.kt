package com.example.wordassociater.date

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BtnDayBinding

class DayBtn(context: Context, attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {
    val b = BtnDayBinding.inflate(LayoutInflater.from(context), this, true)
    private val day: String

    companion object {
        val liveDaySelected = MutableLiveData<String> ()
    }

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

    fun setDayBtn(onDayClicked : (day: String) -> Unit) {
        setObserver()
        b.dayText.text = day
        b.root.setOnClickListener {
            liveDaySelected.value = day
            onDayClicked(day)
        }
    }

    private fun setObserver() {
        liveDaySelected.observe(context as LifecycleOwner) {
            b.dayText.setTextColor(if(it == day) b.root.resources.getColor(R.color.gold) else b.root.resources.getColor(R.color.white))
        }
    }
}