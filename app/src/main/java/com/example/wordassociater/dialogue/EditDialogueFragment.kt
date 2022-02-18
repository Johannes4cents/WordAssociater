package com.example.wordassociater.dialogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.FragmentDialogueEditBinding
import com.example.wordassociater.fire_classes.Bubble
import com.example.wordassociater.fire_classes.Dialogue

class EditDialogueFragment: Fragment() {
    lateinit var b: FragmentDialogueEditBinding
    companion object {
        val dialogue = Dialogue()
        val characterAdapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        var bubbleAdapter = BubbleAdapter()
        var bubbeList = MutableLiveData<List<Bubble>?>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val b = FragmentDialogueEditBinding.inflate(inflater)
        return b.root
    }

    private fun setRecycler() {
        b.characterRecycler.adapter = characterAdapter
        b.bubbleRecycler.adapter = bubbleAdapter
    }

    private fun setObserver() {
        bubbeList.observe(context as LifecycleOwner) {
            bubbleAdapter.submitList(it)
        }
    }

}