package com.example.wordassociater.dialogue

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.character.CharacterRecycler
import com.example.wordassociater.databinding.HolderDialogueBinding
import com.example.wordassociater.fire_classes.Bubble
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Dialogue
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.words.WordAdapter

class DialogueHolder(
        val b: HolderDialogueBinding,
        val dialogueSelectedFunc: (dialogue: Dialogue) -> Unit
): RecyclerView.ViewHolder(b.root) {
    lateinit var dialogue: Dialogue
    lateinit var bubbleAdapter: BubbleAdapter
    lateinit var wordAdapter: WordAdapter
    fun onBind(dialogue: Dialogue) {
        this.dialogue = dialogue


        setContent()
        setBubbleRecycler()
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


    private fun setCharacterRecycler() {
        val liveChars = MutableLiveData<List<Character>>()
        b.characterRecycler.initRecycler(CharacterRecycler.Mode.Preview, liveChars, null)
        liveChars.value = dialogue.getCharacter()
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