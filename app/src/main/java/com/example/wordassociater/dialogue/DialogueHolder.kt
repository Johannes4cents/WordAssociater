package com.example.wordassociater.dialogue

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.HolderDialogueBinding
import com.example.wordassociater.fire_classes.Bubble
import com.example.wordassociater.fire_classes.Dialogue
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.AdapterType
import com.example.wordassociater.words.WordAdapter

class DialogueHolder(
        val b: HolderDialogueBinding,
        val dialogueSelectedFunc: (dialogue: Dialogue) -> Unit
): RecyclerView.ViewHolder(b.root) {
    lateinit var dialogue: Dialogue
    lateinit var bubbleAdapter: BubbleAdapter
    lateinit var wordAdapter: WordAdapter
    lateinit var characterAdapter: CharacterAdapter
    fun onBind(dialogue: Dialogue) {
        this.dialogue = dialogue


        setContent()
        setBubbleRecycler()
        setWordRecycler()
        setCharacterRecycler()

        setClickListener()

    }

    private fun setContent() {
        b.dialogueText.text = if(dialogue.content != "") dialogue.content else "Dialogue"
    }

    private fun setBubbleRecycler() {
        bubbleAdapter = BubbleAdapter(BubbleAdapter.Mode.READ, ::onBubbleSelected, dialogue.getCharacter())
        b.bubbleRecycler.adapter = bubbleAdapter
        bubbleAdapter.submitList(dialogue.getBubbles())
    }

    private fun setWordRecycler() {
        wordAdapter = WordAdapter(AdapterType.Preview, ::onWordSelected, null)
        b.wordRecycler.adapter = wordAdapter
        wordAdapter.submitList(dialogue.getWords())
    }

    private fun setCharacterRecycler() {
        characterAdapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        b.characterRecycler.adapter = characterAdapter
        characterAdapter.submitList(dialogue.getCharacter())
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            dialogueSelectedFunc(dialogue)
        }
    }

    private fun onBubbleSelected(bubble: Bubble) {

    }

    private fun onWordSelected(word: Word) {

    }


}