package com.example.wordassociater.dialogue

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.buttons.ButtonBubbleAdd
import com.example.wordassociater.databinding.HeaderBubbleBinding
import com.example.wordassociater.fire_classes.Bubble
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.firestore.FireStats

class BubbleHeader(val b: HeaderBubbleBinding, val characterList: List<Character>, val takeBubble: (bubble: Bubble) -> Unit): RecyclerView.ViewHolder(b.root) {
    val buttonList = listOf<ButtonBubbleAdd>(b.addBlue,b.addGreen, b.addYellow,b.addOrange)
    fun onBind() {
        setButtons()
    }

    private fun setButtons() {
        for(i in 0 until characterList.count()) {
            buttonList[i].setupEditDialogue(characterList[i], ::clickCharacter)
        }
    }

    private fun clickCharacter(character: Character, color: Bubble.Color) {
        val bubble = Bubble(
                id = FireStats.getBubbleId(),
                character = character.id,
                color = color
        )

        takeBubble(bubble)
    }
}