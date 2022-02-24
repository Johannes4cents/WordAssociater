package com.example.wordassociater.dialogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentDialogueListBinding
import com.example.wordassociater.fire_classes.Dialogue

class DialogueListFragment: Fragment() {
    lateinit var b : FragmentDialogueListBinding
    lateinit var dialogueAdapter : DialogueAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentDialogueListBinding.inflate(inflater)
        setRecycler()
        setObserver()
        setClickListener()
        return b.root
    }

    private fun setRecycler() {
        dialogueAdapter = DialogueAdapter(::onDialogueSelected)
        b.dialogueRecycler.adapter = dialogueAdapter
    }

    private fun onDialogueSelected(dialogue: Dialogue) {
        EditDialogueFragment.oldDialogue = dialogue
        EditDialogueFragment.dialogue = dialogue
        findNavController().navigate(R.id.action_dialogueListFragment_to_editDialogueFragment)
    }

    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_dialogueListFragment_to_startFragment)
        }
    }

    private fun setObserver() {
        Main.dialogueList.observe(context as LifecycleOwner) {
            dialogueAdapter.submitList(it)
        }
    }
}