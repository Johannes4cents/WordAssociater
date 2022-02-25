package com.example.wordassociater.dialogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.ViewPagerFragment
import com.example.wordassociater.bars.AddStuffBar
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.FragmentDialogueEditBinding
import com.example.wordassociater.fire_classes.Bubble
import com.example.wordassociater.fire_classes.Dialogue
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.fire_classes.WordConnection
import com.example.wordassociater.firestore.*
import com.example.wordassociater.popups.popSearchWord
import com.example.wordassociater.utils.*
import com.example.wordassociater.words.WordAdapter
import com.example.wordassociater.words.WordLinear

class EditDialogueFragment: Fragment() {
    lateinit var b: FragmentDialogueEditBinding
    var index = 0
    companion object {
        var oldDialogue = Dialogue()
        var dialogue = Dialogue(id = FireStats.getStoryPartId())
        val characterAdapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        lateinit var bubbleAdapter: BubbleAdapter
        var bubbleList = MutableLiveData<List<Bubble>?>()
    }

    var liveWordsList = MutableLiveData(WordLinear.selectedWords)

    lateinit var wordAdapter: WordAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentDialogueEditBinding.inflate(inflater)
        setContent()
        setDescription()
        setRecycler()
        setClickListener()
        setObserver()
        b.dialogueDescription.setContentFunc {
            dialogue.content = it
        }
        return b.root

    }

    private fun setContent() {
        b.dialogueDescription.setTextSize(24F)
        b.dialogueDescription.setCenterGravity()
        b.dialogueDescription.setSingleLine()
        bubbleList.value = dialogue.getBubbles()
        index = dialogue.bubbleList.count()

    }

    private fun setDescription() {
        b.dialogueDescription.setTextField(if(dialogue.content != "")dialogue.content else "Dialogue")
    }


    private fun setWordList() {
        SearchHelper.setWordList(dialogue.getWords(), liveWordsList)
    }

    private fun handleWordDeselected() {
        for(wordId in oldDialogue.wordList) {
            if(!dialogue.wordList.contains(wordId)) {
                val word = Main.getWord(wordId)!!
                WordConnection.disconnect(word, dialogue.id)
                word.dialogueList.remove(dialogue.id)
                word.decreaseWordUsed()
                FireWords.update(word.id, "dialogueList", word.dialogueList)
            }
        }
    }

    private fun handleCharacterDeselected() {
        for(charId in oldDialogue.characterList) {
            if(!dialogue.characterList.contains(charId)) {
                val character = Main.getCharacter(charId)
                if(character != null) {
                    character.dialogueList.remove(dialogue.id)
                    FireChars.update(character.id, "dialogueList", character.dialogueList)
                }
            }
        }
    }

    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            dialogue = Dialogue()
            AddStuffBar.selectedCharacters.clear()
            ViewPagerFragment.comingFrom = Page.Start
            findNavController().navigate(R.id.action_editDialogueFragment_to_startFragment)
        }

        b.saveBtn.setOnClickListener {
            saveDialogue()
        }

        b.addWordButton.setOnClickListener {
            popSearchWord(b.addWordButton, ::handleWordSelected, liveWordsList)
        }

        b.dialogueDescription.setContentFunc {
            dialogue.content = it
        }
    }

    private fun handleWordSelected(word: Word) {
        word.selected = !word.selected
        if(word.selected) {
            if(!dialogue.wordList.contains(word.id)) dialogue.wordList.add(word.id)
        }
        else dialogue.wordList.remove(word.id)

        SearchHelper.setWordList(dialogue.getWords(), liveWordsList)
    }


    private fun saveDialogue() {
        if(bubbleList.value != null && bubbleList.value!!.isNotEmpty()) {
            val list = Bubble.toIdList(bubbleList.value!!)
            dialogue.bubbleList = list
            dialogue.characterList = getCharList()
            FireDialogue.add(dialogue, requireContext())
            for(bubble in bubbleList.value!!) {
                if(!Main.bubbleList.value!!.contains(bubble)) FireBubbles.add(bubble, requireContext())
            }
            for(c in dialogue.characterList) {
                val char = Main.getCharacter(c)!!
                if(!char.dialogueList.contains(dialogue.id)) {
                    char.dialogueList.add(dialogue.id)
                    FireChars.update(c,"dialogueList", char.dialogueList)
                }
            }
            for(w in dialogue.wordList) {
                val word = Main.getWord(w)
                if(word != null) {
                    if(!word.dialogueList.contains(dialogue.id)) {
                        word.dialogueList.add(dialogue.id)
                        FireWords.update(word.id, "dialogueList", word.dialogueList)
                        word.increaseWordUsed()
                    }
                }
            }

            handleWordDeselected()
            handleCharacterDeselected()
            WordConnection.connect(dialogue)
            dialogue = Dialogue(id = FireStats.getStoryPartId())
            findNavController().navigate(R.id.action_editDialogueFragment_to_startFragment)
        }
        else {
            Helper.toast("No Dialogue added yet", requireContext())
        }

    }

    private fun getCharList(): MutableList<Long> {
        val idList = mutableListOf<Long>()
        if(bubbleList.value != null) {
            for(b in bubbleList.value!!) {
                if(!idList.contains(b.character)) idList.add(b.character)
            }
        }
        return idList
    }

    private fun handleBubble(bubble: Bubble) {
        val newList = bubbleList.value?.toMutableList() ?: mutableListOf()
        bubble.index = dialogue.currentIndex
        dialogue.currentIndex = dialogue.currentIndex + 1
        newList.add(bubble)
        bubbleList.value = newList.sortedBy { b -> b.index }
    }

    private fun setRecycler() {
        bubbleAdapter = BubbleAdapter(BubbleAdapter.Mode.WRITE,::handleBubble, dialogue.getCharacter())
        b.characterRecycler.adapter = characterAdapter
        b.bubbleRecycler.adapter = bubbleAdapter

        val callback: ItemTouchHelper.Callback = SwipeToDeleteCallback(bubbleAdapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(b.bubbleRecycler)


        characterAdapter.submitList(dialogue.getCharacter())

        wordAdapter = WordAdapter(AdapterType.Preview, ::takeWordFunc, null)
        b.wordRecycler.adapter = wordAdapter
        wordAdapter.submitList(dialogue.getWords())
        setWordList()


    }

    private fun takeWordFunc(word: Word) {

    }

    private fun setObserver() {
        var bubbleHeader = Bubble()
        bubbleHeader.isHeader = true
        bubbleAdapter.submitList(listOf(bubbleHeader))

        bubbleList.observe(context as LifecycleOwner) {
            val noDeletedBubbles = it?.filter { b -> !b.deleted }
            if(it != null && (noDeletedBubbles != null)) {
                bubbleAdapter.submitList(noDeletedBubbles + bubbleHeader)
            }
            else bubbleAdapter.submitList(listOf(bubbleHeader))
        }

        liveWordsList.observe(context as LifecycleOwner) {
            val selectedWords = mutableListOf<Word>()
            for(w in it) {
                if(w.selected && !selectedWords.contains(w)) selectedWords.add(w)
            }
            wordAdapter.submitList(selectedWords)
        }
    }

}