package com.example.wordassociater.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentNoteEditBinding
import com.example.wordassociater.fire_classes.Note
import com.example.wordassociater.firestore.FireNotes

class EditNoteFragment: Fragment() {
    lateinit var b : FragmentNoteEditBinding
    companion object {
        lateinit var note: Note
    }

    private var noteType = Note.Type.GameApp

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentNoteEditBinding.inflate(inflater)
        setRadioClicks()
        selectInitialButton()
        setContent()
        setClickListener()

        return b.root
    }

    private fun setRadioClicks() {
        b.radioOther.setOnClickListener {
            noteType = Note.Type.Other
        }
        b.radioWords.setOnClickListener {
            noteType = Note.Type.WordsApp
        }
        b.radioStory.setOnClickListener {
            noteType = Note.Type.Story
        }
        b.radioGameApp.setOnClickListener {
            noteType = Note.Type.GameApp
        }
    }

    private fun setClickListener() {
        b.saveBtn.setOnClickListener {
            updateNote()
        }

        b.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_editNoteFragment_to_notesFragment)
        }
    }

    private fun setContent() {
        b.noteInput.setText(note.content)
    }

    private fun selectInitialButton() {
        when (note.type) {
            Note.Type.Story -> {
                noteType = Note.Type.Story;
                b.radioStory.isChecked = true
            }
            Note.Type.WordsApp -> {
                noteType = Note.Type.WordsApp
                b.radioWords.isChecked = true
            }
            Note.Type.GameApp -> {
                noteType = Note.Type.GameApp
                b.radioGameApp.isChecked = true
            }
            Note.Type.Other -> {
                noteType = Note.Type.Other
                b.radioOther.isChecked = true
            }
        }
    }

    private fun updateNote() {
        handleTypeChange()
        note.content = b.noteInput.text.toString()
        FireNotes.add(note, requireContext())
        findNavController().navigate(R.id.action_editNoteFragment_to_notesFragment)
    }

    private fun handleTypeChange() {
        if(noteType != note.type) {
            FireNotes.delete(note, b.root.context)
        }

        note.type = noteType
    }

}