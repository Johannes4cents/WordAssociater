package com.example.wordassociater.display_filter

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DisplayFilterRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    private lateinit var forWhat: FilterOption.For
    private lateinit var filterLiveList: MutableLiveData<List<FilterOption>>
    private lateinit var optionAdapter: FilterOptionAdapter

    fun initRecycler(
            filterLiveList: MutableLiveData<List<FilterOption>>,
            onFilterSelected: (option: FilterOption) -> Unit,
            forWhat: FilterOption.For) {
        this.filterLiveList = filterLiveList
        this.forWhat = forWhat
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        optionAdapter = FilterOptionAdapter(onFilterSelected)
        adapter = optionAdapter
        setObserver()
    }

    private fun setObserver() {
        filterLiveList.observe(context as LifecycleOwner) {
            optionAdapter.submitList(
                    when(forWhat) {
                        FilterOption.For.Bar -> it.filter { o -> o.forWhat == FilterOption.For.Bar }
                        FilterOption.For.Icon -> it.filter { o -> o.forWhat == FilterOption.For.Icon }
                        FilterOption.For.Other -> it.filter { o -> o.forWhat == FilterOption.For.Other }
                        FilterOption.For.Content -> it.filter { o -> o.forWhat == FilterOption.For.Content }
                    }
            )
            optionAdapter.notifyDataSetChanged()
        }
    }

}