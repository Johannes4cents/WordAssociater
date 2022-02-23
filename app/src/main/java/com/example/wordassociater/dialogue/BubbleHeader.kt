package com.example.wordassociater.dialogue

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.buttons.ButtonBubbleAdd
import com.example.wordassociater.databinding.HeaderBubbleBinding
import com.example.wordassociater.fire_classes.Bubble
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.firestore.FireStats

class BubbleHeader(val b: HeaderBubbleBinding, val characterList: List<Character>?, val takeBubble: (bubble: Bubble) -> Unit): RecyclerView.ViewHolder(b.root) {
    private val buttonList = listOf<ButtonBubbleAdd>(b.addBlue,b.addGreen, b.addYellow,b.addOrange)
    fun onBind() {
        setLeftRightCharacters()
        setButtons()
    }

    private fun setLeftRightCharacters() {
        var isLeft = true
        if(characterList != null) {
            for(c in characterList) {
                c.isLeft = isLeft
                isLeft = !isLeft
            }
        }
    }

    private fun setButtons() {
        if(characterList != null) {
            for(i in 0 until characterList.count()) {
                buttonList[i].setupEditDialogue(characterList[i], ::clickCharacter)
            }
        }

    }

    private fun clickCharacter(character: Character, color: Bubble.Color) {
        val bubble = Bubble(
                id = FireStats.getBubbleId(b.root.context),
                character = character.id,
                color = color
        )

        takeBubble(bubble)
    }
}