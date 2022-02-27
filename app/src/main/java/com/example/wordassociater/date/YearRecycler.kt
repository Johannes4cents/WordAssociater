package com.example.wordassociater.date

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class YearRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    lateinit var yearAdapter: YearAdapter
    lateinit var onYearSelected: (year: String) -> Unit
    val liveDate = MutableLiveData<String>()
    fun initRecycler(year: String, onYearSelected: (year: String) -> Unit) {
        this.onYearSelected = onYearSelected
        liveDate.value = year
        yearAdapter = YearAdapter(::interceptOnClick, liveDate)
        adapter = yearAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        getTheList()

    }

    private fun interceptOnClick(year:String) {
        onYearSelected(year)
        liveDate.value = year
    }

    private fun getTheList() {
        var index = 0
        var indexList = 0
        val yearList = mutableListOf<String>()
        for(i in 1880..2055) {
            if(i.toString() == liveDate.value!!) indexList = index
            yearList.add(i.toString())
            index++
        }
        yearAdapter.submitList(yearList)
        scrollToPosition(if(indexList != 0 ) indexList else yearAdapter.currentList.count() - 35)

    }
}