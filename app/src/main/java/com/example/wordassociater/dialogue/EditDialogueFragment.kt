package com.example.wordassociater.dialogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.R
import com.example.wordassociater.ViewPagerFragment
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.FragmentDialogueEditBinding
import com.example.wordassociater.fire_classes.Bubble
import com.example.wordassociater.fire_classes.Dialogue
import com.example.wordassociater.firestore.FireBubbles
import com.example.wordassociater.firestore.FireDialogue
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.Page

class EditDialogueFragment: Fragment() {
    lateinit var b: FragmentDialogueEditBinding
    var index = 0
    companion object {
        val dialogue = Dialogue()
        val characterAdapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        lateinit var bubbleAdapter: BubbleAdapter
        var bubbleList = MutableLiveData<List<Bubble>?>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentDialogueEditBinding.inflate(inflater)
        setRecycler()
        setClickListener()
        setObserver()
        return b.root
    }

    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            ViewPagerFragment.comingFrom = Page.Start
            findNavController().navigate(R.id.action_editDialogueFragment_to_startFragment)
        }

        b.saveBtn.setOnClickListener {
            saveDialogue()
        }
    }

    private fun saveDialogue() {
        if(bubbleList.value != null && bubbleList.value!!.isNotEmpty()) {
            val list = Bubble.toIdList(bubbleList.value!!)
            dialogue.content = list
            dialogue.id = FireStats.getDialogueId()
            dialogue.charList = getCharList()
            FireDialogue.add(dialogue, requireContext())
            for(bubble in bubbleList.value!!) {
                FireBubbles.add(bubble, requireContext())
            }
        }
        else {
            Helper.toast("No Dialogue added yet", requireContext())
        }

    }

    private fun getCharList(): MutableList<Long> {
        val idList = mutableListOf<Long>()
        if(bubbleList.value != null) {
            for(b in bubbleList.value!!) {
                idList.add(b.character)
            }
        }
        return idList
    }

    private fun handleBubble(bubble: Bubble) {
        val newList = bubbleList.value?.toMutableList() ?: mutableListOf()
        bubble.index = index
        index++
        newList.add(bubble)
        bubbleList.value = newList
    }

    private fun setRecycler() {
        bubbleAdapter = BubbleAdapter(::handleBubble, dialogue.getCharacter())
        b.characterRecycler.adapter = characterAdapter
        b.bubbleRecycler.adapter = bubbleAdapter

        characterAdapter.submitList(dialogue.getCharacter())
    }

    private fun setObserver() {

        var bubbleHeader = Bubble()
        bubbleHeader.isHeader = true
        bubbleAdapter.submitList(listOf(bubbleHeader))

        bubbleList.observe(context as LifecycleOwner) {

            if(it != null) bubbleAdapter.submitList(it + bubbleHeader)
            else bubbleAdapter.submitList(listOf(bubbleHeader))
        }
    }

}