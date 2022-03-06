package com.example.wordassociater.items

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderSnippetPartPopupBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Item
import com.example.wordassociater.live_recycler.LiveHolder
import com.example.wordassociater.utils.LiveClass

class ItemHolderPopUp(val b: HolderSnippetPartPopupBinding): RecyclerView.ViewHolder(b.root), LiveHolder {
    override lateinit var item: LiveClass
    var onClick: ((item: LiveClass) -> Unit)? = null

    override fun onBind(item: LiveClass, takeItemFunc:((item: LiveClass) -> Unit)?) {
        this.item = item
        onClick = takeItemFunc
        item as Item

        setObserver()
        setBackGroundColorFirstTime()
        setClickListener()
        setContent()
    }

    private fun setContent() {
        b.partName.text = (item as Item).name
        if((item as Item).imgUrl != "") Glide.with(b.partImage).load((item as Item).imgUrl).into(b.partImage)
        else b.partImage.setImageResource((item as Item).getImage().getDrawable())

        b.checkbox.setImageResource(
                if(item.selected) R.drawable.checked_box else R.drawable.checkbox_unchecked
        )
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            onClick!!(item)
        }
    }

    private fun setObserver() {
        DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
            b.partName.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else  b.root.context.resources.getColor(
                    R.color.black))
        }
        DisplayFilter.observeItemColorDark(b.root.context, b.root, listOf(b.partName))
    }

    private fun setBackGroundColorFirstTime() {
        var dark = DisplayFilter.itemColorDark.value!!
        b.root.setBackgroundColor(if(dark) b.root.context.resources.getColor(R.color.snippets) else b.root.context.resources.getColor(
                R.color.white))
        b.partName.setTextColor(if(dark) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(
                R.color.black))
    }

}