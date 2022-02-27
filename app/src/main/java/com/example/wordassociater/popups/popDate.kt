package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import com.example.wordassociater.databinding.SelectorDateBinding
import com.example.wordassociater.utils.Date
import com.example.wordassociater.utils.Helper

fun popDate(from: View, onDateEntered: (date: Date) -> Unit, oldDate: Date?) {
    val b = SelectorDateBinding.inflate(LayoutInflater.from(from.context), null, false)
    val inputFieldList = listOf(b.dayInput, b.monthInput, b.yearInput)
    val pop = Helper.getPopUp(b.root, from, null, null)
    val date = oldDate ?: Date()

    for(field in inputFieldList) {
        field.setCenterGravity()
        field.setInputTypeNumbers()
        field.setSingleLine()
        field.setMaxInput(2)
        field.hideOnEnter()
    }

    b.yearInput.setMaxInput(4)

    b.dayInput.setContentFunc {
        if(it != "" && it != " ") date.day = it.toInt()
    }

    b.yearInput.setContentFunc {
        if(it != "" && it != " ") date.year = it.toInt()
    }

    b.saveDate.setOnClickListener {
        onDateEntered(date)
        pop.dismiss()
    }
}