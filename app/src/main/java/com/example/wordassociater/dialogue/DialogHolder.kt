package com.example.wordassociater.dialogue

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderBubbleBinding
import com.example.wordassociater.fire_classes.Bubble
import com.example.wordassociater.utils.Helper

class BubbleHolder(val b: HolderBubbleBinding, val takeBubble: (bubble: Bubble)-> Unit): RecyclerView.ViewHolder(b.root) {
    lateinit var bubble: Bubble
    var inEdit = MutableLiveData(false)
    fun onBind(bubble: Bubble) {
        this.bubble = bubble
        setClickListener()
        setObserver()
        setBackground()
        openInput()
    }

    private fun setClickListener() {
        b.dialogueContent.setOnClickListener {
            openInput()
            inEdit.value = true
        }

        b.deleteSaveBtn.setOnClickListener {
            if(inEdit.value!!) {
                bubble.content = b.dialogueInput.text.toString()
                takeBubble(bubble)
                closeInput()
            }
            else {
                bubble.markedForDelete = true
                takeBubble(bubble)
            }
        }
    }

    private fun setBackground() {
        b.bubbleBackground.setBackgroundResource(Bubble.getColor(bubble.color))
    }

    private fun setObserver() {
        inEdit.observe(b.root.context as LifecycleOwner) {
            if(it) {
                b.deleteSaveBtn.setImageResource(R.drawable.btn_snippet_save)
            }
            else {
                b.deleteSaveBtn.setImageResource(R.drawable.icon_red_x)
            }
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
        inEdit.value = true
    }

    private fun closeInput() {
        b.dialogueContent.visibility = View.VISIBLE
        b.dialogueInput.visibility = View.GONE
        b.dialogueInput.setText("")
        Helper.getIMM(b.root.context).hideSoftInputFromWindow(b.dialogueInput.windowToken, 0)
        inEdit.value = false
    }
}

