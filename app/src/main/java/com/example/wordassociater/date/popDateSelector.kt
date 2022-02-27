package com.example.wordassociater.date

import android.view.LayoutInflater
import android.view.View
import com.example.wordassociater.databinding.PopSelectDateBinding
import com.example.wordassociater.utils.Date
import com.example.wordassociater.utils.Helper


fun popDateSelector(view: View, date: Date, onDateSet : (date: Date) -> Unit) {
    val b = PopSelectDateBinding.inflate(LayoutInflater.from(view.context))
    val pop = Helper.getPopUp(b.root, view,null,null, null, -100, true)
    val dayList = listOf(
            b.day1, b.day2, b.day3, b.day4, b.day5,b.day6,b.day7,b.day8,b.day9,b.day10, b.day11, b.day12,b.day13,b.day14,b.day15,b.day16,
            b.day17,b.day18,b.day19,b.day20,b.day21,b.day22,b.day23,b.day24,b.day25,b.day26,b.day27,b.day28,b.day29,b.day30,b.day31)

    val monthsList = listOf(b.month1, b.month2, b.month3, b.month4,b.month5,b.month6,b.month7,b.month8,b.month9,b.month10,b.month11,b.month12)

    for(dayBtn in dayList) {
        dayBtn.setDayBtn {
            date.day = it.toInt()
        }
    }

    for(month in monthsList) {
        month.setMonthBtn {
            date.month = it
        }
    }

    b.yearRecycler.initRecycler(date.year.toString()) {
        date.year = it.toInt()
    }

    DayBtn.liveDaySelected.value = date.day.toString()
    MonthBtn.liveMonthSelected.value = date.month.toString()
    b.yearRecycler.liveDate.value = date.year.toString()

    b.btnBack.setOnClickListener {
        pop.dismiss()
    }

    b.btnSave.setOnClickListener {
        onDateSet(date)
        pop.dismiss()
    }




}