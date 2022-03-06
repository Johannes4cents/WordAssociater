package com.example.wordassociater.words

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderWordPopupBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.live_recycler.LiveHolder
import com.example.wordassociater.utils.LiveClass

class WordHolderPopup(
        val b : HolderWordPopupBinding,
): RecyclerView.ViewHolder(b.root), LiveHolder {
    override lateinit var item : LiveClass
    var takeItemFunc:((item: LiveClass) -> Unit)? = null


    override fun onBind(item: LiveClass, takeItemFunc:((item: LiveClass) -> Unit)?) {
        this.item = item
        this.takeItemFunc = takeItemFunc
        setContent()
        setClickListener()
        setObserver()
    }

    private fun setContent() {
        b.content.text = (item as Word).text
        b.checkbox.setImageResource(if(item.selected) R.drawable.checked_box else R.drawable.checkbox_unchecked)
        b.idField.text = item.id.toString()
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            b.checkbox.setImageResource(if(!item.selected) R.drawable.checked_box else R.drawable.checkbox_unchecked)
            takeItemFunc!!(item)
        }
    }

    private fun setObserver() {
        DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
            b.content.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
        }
    }
}