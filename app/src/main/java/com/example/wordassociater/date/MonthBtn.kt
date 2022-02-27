package com.example.wordassociater.date

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BtnMonthBinding

class MonthBtn(context: Context, attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {
    val b = BtnMonthBinding.inflate(LayoutInflater.from(context), this, true)
    var month: String

    companion object {
        val liveMonthSelected = MutableLiveData<String>()
    }

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

    fun setMonthBtn(onDayClicked : (day: String) -> Unit) {
        setObserver()
        b.monthText.text = month
        b.root.setOnClickListener {
            liveMonthSelected.value = month
            onDayClicked(month)
        }
    }

    private fun setObserver() {
        liveMonthSelected.observe(context as LifecycleOwner) {
            b.monthText.setTextColor(if(it == month) b.root.resources.getColor(R.color.gold) else b.root.resources.getColor(R.color.white))
        }
    }
}