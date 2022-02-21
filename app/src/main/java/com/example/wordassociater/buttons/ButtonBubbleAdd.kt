package com.example.wordassociater.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.wordassociater.R
import com.example.wordassociater.databinding.ButtonBubbleAddBinding
import com.example.wordassociater.fire_classes.Bubble
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.utils.Helper

class ButtonBubbleAdd(context: Context, attributeSet: AttributeSet?): ConstraintLayout(context, attributeSet) {
    val b = ButtonBubbleAddBinding.inflate(LayoutInflater.from(context), this, true)
    var bubbleColor: Bubble.Color
    lateinit var character: Character

    init {
        context.theme.obtainStyledAttributes(
                attributeSet,
                R.styleable.ButtonBubbleAdd,
                0, 0).apply {

            try {
                var colorEnum = getInteger(R.styleable.ButtonBubbleAdd_bubbleColor, 0)
                bubbleColor = when(colorEnum) {
                    0 -> Bubble.Color.Blue
                    1 -> Bubble.Color.Yellow
                    2 -> Bubble.Color.Green
                    3 -> Bubble.Color.Orange
                    else -> Bubble.Color.Green
                }
            } finally {
                recycle()
            }
            b.bubbleBG.setImageResource(Bubble.getAddBubbleBgNoDots(bubbleColor))
        }
    }

    private fun setName(name: String) {
        b.characterName.text = Helper.setStringToMultipleLines(name)
    }

    fun setupEditDialogue(character: Character, clickCharacter: (char: Character, color: Bubble.Color) -> Unit) {
        b.root.visibility = View.VISIBLE
        this.character = character
        setName(character.name)
        setupClickListener(clickCharacter)

    }

    fun setupClickListener(clickFunc: (char: Character, color: Bubble.Color) -> Unit) {
        b.bubbleBG.setOnClickListener {
            clickFunc(character, bubbleColor)
        }
    }



}