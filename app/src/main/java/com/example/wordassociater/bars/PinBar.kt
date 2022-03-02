package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarPinBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Snippet

class PinBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarPinBinding.inflate(LayoutInflater.from(context), this, true)
    val attachedSnippet = MutableLiveData<Snippet?>()

    init {
        setObserver()
    }

    fun attachSnippet(snippet: Snippet) {
        attachedSnippet.value = snippet
    }

    private fun setObserver() {
        DisplayFilter.barColorDark.observe(context as LifecycleOwner) {
            b.root.setBackgroundColor(if(it) b.root.context.resources.getColor(R.color.snippetsLite) else b.root.context.resources.getColor(R.color.white))
        }
    }
}