package com.example.wordassociater.image

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderImageBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.live_recycler.LiveHolder
import com.example.wordassociater.utils.Image
import com.example.wordassociater.utils.LiveClass

class ImageHolder(val b: HolderImageBinding): RecyclerView.ViewHolder(b.root), LiveHolder {
    var color = R.color.snippets
    companion object {
        val selectedImage = MutableLiveData<Image>()
    }
    override lateinit var item: LiveClass

    override fun onBind(item: LiveClass, takeItemFunc: (item: LiveClass) -> Unit) {
        this.item = item
        item as Image

        b.root.setOnClickListener {
            selectedImage.value = item
            takeItemFunc(item)
        }

        selectedImage.observe(b.root.context as LifecycleOwner) {
            b.root.setBackgroundColor(
                    if(it == item) b.root.context.resources.getColor(R.color.lightYellow) else b.root.context.resources.getColor(color)
            )
        }

        DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
            color = if(it) R.color.snippets else R.color.white
        }

        // set Color on Startup
        val darkBar = DisplayFilter.barColorDark.value!!
        color = if(darkBar) R.color.snippets else R.color.white
    }

}