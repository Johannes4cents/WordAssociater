package com.example.wordassociater.words

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderWordSimpleBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Word

class WordHolderList(
        val b : HolderWordSimpleBinding,
        private  val takeWordFunc: (word: Word) -> Unit,
        ): RecyclerView.ViewHolder(b.root) {
    private lateinit var word : Word

    fun onBind(word: Word) {
        this.word = word
        setContent()
        setClickListener()
        setObserver()
    }

    private fun setContent() {
        b.content.text = word.text
        b.checkbox.setImageResource(if(word.selected) R.drawable.checked_box else R.drawable.checkbox_unchecked)

        b.stemCount.text = word.stems.count().toString()
        b.idField.text = word.id.toString()
        b.usedOrConnectionsField.text = word.used.toString()
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            b.checkbox.setImageResource(if(!word.selected) R.drawable.checked_box else R.drawable.checkbox_unchecked)
            takeWordFunc(word)
        }
    }

    private fun setObserver() {
            DisplayFilter.barColorDark.observe(b.root.context as LifecycleOwner) {
                b.content.setTextColor(if(it) b.root.context.resources.getColor(R.color.white) else b.root.context.resources.getColor(R.color.black))
            }
    }
        }