package com.example.wordassociater.display_filter

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.PopDisplayFilterBinding
import com.example.wordassociater.utils.Helper

fun popDisplayFilter(
        from: View, liveList: MutableLiveData<List<FilterOption>>,
        onOptionClicked: (option: FilterOption) -> Unit,
       ) {

    val b = PopDisplayFilterBinding.inflate(LayoutInflater.from(from.context))
    val pop = Helper.getPopUp(b.root, from)

    b.barOptionRecycler.initRecycler(liveList, onOptionClicked, FilterOption.For.Bar)
    b.iconOptionRecycler.initRecycler(liveList, onOptionClicked, FilterOption.For.Icon)
    b.contentOptionRecycler.initRecycler(liveList, onOptionClicked, FilterOption.For.Content)


    b.btnToggleBarColor.setOnClickListener {
        onOptionClicked(FilterOption.options.find { o -> o.type == FilterOption.Type.BarColorDark }!!)
    }

    DisplayFilter.barColorDark.observe(from.context as LifecycleOwner) {
        b.toggleDescription.setTextColor(if(it) from.context.resources.getColor(R.color.black) else from.context.resources.getColor(R.color.white))
        b.toggleText.setTextColor(if(!it) from.context.resources.getColor(R.color.black) else from.context.resources.getColor(R.color.white))
        b.toggleLinear.setBackgroundColor(if(it) from.context.resources.getColor(R.color.white) else from.context.resources.getColor(R.color.snippets))
        b.btnToggleBarColor.setBackgroundColor(if(it) from.context.resources.getColor(R.color.snippets) else from.context.resources.getColor(R.color.white))
        b.toggleText.text = if(it) "Dark" else "Light"
    }
}