package com.example.wordassociater.notes

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentNotesBinding
import com.example.wordassociater.fire_classes.Note
import com.example.wordassociater.firestore.FireNotes
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.utils.Helper

class NotesFragment: Fragment() {
    lateinit var b : FragmentNotesBinding
    companion object {
        val noteAdapter = NoteAdapter()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentNotesBinding.inflate(inflater)
        setRecycler()
        setObserver()
        setClickListener()
        return b.root
    }

    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_startFragment)
        }

        b.saveBtn.setOnClickListener {
            saveNote()
        }

        b.noteInput.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                saveNote()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun setRecycler() {
        b.noteRecycler.adapter = noteAdapter
        noteAdapter.submitList(Main.notesList.value)
    }

    private fun setObserver() {
        Main.notesList.observe(viewLifecycleOwner) {
            if(it != null) {
                noteAdapter.submitList(it)
            }
        }
    }

    private fun saveNote() {
        if(b.noteInput.text.isNotEmpty()) {
            val newNote = Note(FireStats.getNoteId(), b.noteInput.text.toString())
            FireNotes.add(newNote, requireContext())
            b.noteInput.setText("")
        }
        else {
            Toast.makeText(context, "Note can't be empty", Toast.LENGTH_SHORT).show()
        }
        Helper.getIMM(requireContext()).hideSoftInputFromWindow(b.noteInput.windowToken, 0)
    }




}