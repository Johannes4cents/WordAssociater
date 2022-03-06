package com.example.wordassociater.character

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderSnippetPartPopupBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.live_recycler.LiveHolder
import com.example.wordassociater.utils.LiveClass

class CharacterHolderPopUp(val b: HolderSnippetPartPopupBinding): RecyclerView.ViewHolder(b.root), LiveHolder {
    override lateinit var item: LiveClass
    var takeCharacterFunc: ((char: Character) -> Unit)? = null
    override fun onBind(item: LiveClass, takeItemFunc:((item: LiveClass) -> Unit)?) {
        this.item = item
        this.takeCharacterFunc = takeItemFunc
        setContent()
        setClickListener()
        setObserver()
        setBackGroundColorFirstTime()

    }

    private fun setContent() {
        Glide.with(b.root).load((item as Character).imgUrl).into(b.partImage)
        b.partName.text = (item as Character).name

        b.checkbox.setImageResource(
                if(item.selected) R.drawable.checked_box else R.drawable.checkbox_unchecked
        )

    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            takeCharacterFunc!!((item as Character))
        }
    }

    private fun setObserver() {
        DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
            b.partName.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else  b.root.context.resources.getColor(R.color.black))
        }
        DisplayFilter.observeItemColorDark(b.root.context, b.root, listOf(b.partName))
    }

    private fun setBackGroundColorFirstTime() {
        var dark = DisplayFilter.itemColorDark.value!!
        b.root.setBackgroundColor(if(dark) b.root.context.resources.getColor(R.color.snippets) else b.root.context.resources.getColor(R.color.white))
        b.partName.setTextColor(if(dark) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
    }



}
