package com.example.wordassociater.drama

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderDramaTypeBinding
import com.example.wordassociater.utils.Drama

class DramaTypeHolder(val b: HolderDramaTypeBinding, val takeDramaTypeFunc: (dramaType: Drama) -> Unit): RecyclerView.ViewHolder(b.root) {
    lateinit var dramaType: Drama
    fun onBind(type: Drama) {
        this.dramaType = type
        setIcon()
        setName()
        setClickListener()
    }

    private fun setIcon() {
    }

    private fun setName() {
        b.typeName.text = dramaType.name
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            takeDramaTypeFunc(dramaType)
        }
    }
}