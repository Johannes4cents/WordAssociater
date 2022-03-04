package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarImportanceBinding

class ImportanceBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarImportanceBinding.inflate(LayoutInflater.from(context), this, true)
    private val mentionedImages = listOf(b.mentioned, b.main, b.side)
    private val selectedImportance = MutableLiveData<ImageView>()

    init {
        setObserver()
    }

    fun setMentionedFunc(func: () -> Unit) {
        b.mentioned.setOnClickListener {
            selectedImportance.value = it as ImageView
            func()
        }
    }

    fun setSideFunc(func: () -> Unit) {
        b.side.setOnClickListener {
            selectedImportance.value = it as ImageView
            func()
        }
    }

    fun setMainFunc(func: () -> Unit) {
        b.main.setOnClickListener {
            selectedImportance.value = it as ImageView
            func()
        }
    }

    fun setObserver() {
        selectedImportance.observe(context as LifecycleOwner) {
            b.mentioned.setImageResource(if(it == b.mentioned) R.drawable.importance_mentioned_selected else R.drawable.importance_mentioned)
            b.side.setImageResource(if(it == b.side) R.drawable.importance_side_selected else R.drawable.importance_side)
            b.main.setImageResource(if(it == b.main) R.drawable.importance_main_selected else R.drawable.importance_main)
        }
    }
}