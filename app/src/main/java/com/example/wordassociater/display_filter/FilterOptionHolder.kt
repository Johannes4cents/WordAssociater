package com.example.wordassociater.display_filter

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderFilterOptionBinding

class FilterOptionHolder(val b: HolderFilterOptionBinding, val onOptionClicked: (filterOption: FilterOption) -> Unit): RecyclerView.ViewHolder(b.root) {
    lateinit var filterOption: FilterOption

    fun onBind(filterOption: FilterOption) {
        this.filterOption = filterOption
        b.filterName.text = filterOption.name

        b.root.setOnClickListener {
            onOptionClicked(filterOption)
        }

        b.checkbox.setImageResource(if(filterOption.selected) R.drawable.checked_box else R.drawable.checkbox_unchecked)
        b.optionIcon.setImageResource(filterOption.icon)
    }
}