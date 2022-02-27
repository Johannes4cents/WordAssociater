package com.example.wordassociater.date

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderYearBinding

class YearHolder(val b: HolderYearBinding, val onYearSelected: (year : String) -> Unit): RecyclerView.ViewHolder(b.root) {
    lateinit var year: String
    lateinit var liveDate: MutableLiveData<String>
    fun onBind(year: String, liveDate: MutableLiveData<String>) {
        this.liveDate = liveDate
        this.year = year
        b.yearText.text = year

        b.root.setOnClickListener {
            onYearSelected(year)
        }

        liveDate.observe(b.root.context as LifecycleOwner) {
            b.yearText.setTextColor(if(it == year) b.root.resources.getColor( R.color.gold) else b.root.resources.getColor(R.color.white))
        }
    }
}