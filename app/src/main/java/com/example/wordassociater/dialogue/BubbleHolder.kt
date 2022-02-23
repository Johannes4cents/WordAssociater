package com.example.wordassociater.dialogue

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.Main
import com.example.wordassociater.databinding.HolderBubbleBinding
import com.example.wordassociater.fire_classes.Bubble
import com.example.wordassociater.utils.Helper

class BubbleHolder(val mode: BubbleAdapter.Mode,val b: HolderBubbleBinding, val takeBubble: (bubble: Bubble)-> Unit): RecyclerView.ViewHolder(b.root) {
    lateinit var bubble: Bubble
    fun onBind(bubble: Bubble) {
        this.bubble = bubble
        setWordInput()
        setContent()
        setClickListener()
        setObserver()
        setBackground()
        handleBumper()
    }

    private fun setWordInput() {
        b.wordsInputField.setContentFunc(::takeContentFunc)
        if(mode == BubbleAdapter.Mode.READ) b.wordsInputField.enableInput(false)
    }

    private fun setContent() {
        b.wordsInputField.setTextField(bubble.content)
    }

    private fun handleBumper() {
        if(mode == BubbleAdapter.Mode.WRITE) {
            if(Main.getCharacter(bubble.character)?.isLeft == true) {
                val char = Main.getCharacter(bubble.character)
                if(char?.imgUrl != "") Glide.with(b.root).load(char?.imgUrl).into(b.portraitLeft)
                bubble.isLeft = true
                b.portraitLeft.visibility = View.VISIBLE
            }
            else {
                bubble.isLeft = false
                val char = Main.getCharacter(bubble.character)
                if(char?.imgUrl != "") Glide.with(b.root).load(char?.imgUrl).into(b.portraitRight)
                b.portraitRight.visibility = View.VISIBLE
            }
        }
        else {
            if(bubble.isLeft) {
                val char = Main.getCharacter(bubble.character)
                if(char?.imgUrl != "") Glide.with(b.root).load(char?.imgUrl).into(b.portraitLeft)
                b.portraitLeft.visibility = View.VISIBLE
            }
            else {
                val char = Main.getCharacter(bubble.character)
                if(char?.imgUrl != "") Glide.with(b.root).load(char?.imgUrl).into(b.portraitRight)
                b.portraitRight.visibility = View.VISIBLE
            }
        }
    }

    private fun takeContentFunc(content: String) {
        Helper.toast(content, b.root.context)
        bubble.content = content
    }

    private fun setClickListener() {

    }

    private fun setBackground() {
        b.bubbleBackground.setBackgroundResource(Bubble.getColor(bubble.color))
    }

    private fun setObserver() {
    }

}

