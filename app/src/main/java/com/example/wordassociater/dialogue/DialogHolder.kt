package com.example.wordassociater.dialogue

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderBubbleBinding
import com.example.wordassociater.fire_classes.Dialogue
import com.example.wordassociater.utils.Helper

class DialogHolder(val b: HolderBubbleBinding): RecyclerView.ViewHolder(b.root) {
    lateinit var dialogue: Dialogue
    var inEdit = false
    fun onBind(dialogue: Dialogue) {
        this.dialogue = dialogue
    }

    private fun setClickListener() {
        b.dialogueContent.setOnClickListener {
            openInput()
        }

    }

    fun openInput() {
        b.dialogueContent.visibility = View.GONE
        b.dialogueInput.visibility = View.VISIBLE
        b.dialogueInput.setText(b.dialogueContent.text.toString())
        b.dialogueInput.isFocusable = true
        b.dialogueInput.isFocusableInTouchMode = true
        b.dialogueInput.requestFocus()
        Helper.getIMM(b.root.context).showSoftInput(b.dialogueInput, 0)
        inEdit = true
    }
}

