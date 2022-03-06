package com.example.wordassociater.notes

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderNoteBinding
import com.example.wordassociater.fire_classes.Note
import com.example.wordassociater.firestore.FireNotes
import com.example.wordassociater.popups.popConfirmation

class NoteHolder(val b : HolderNoteBinding, val takeNoteFunc: (note: Note) -> Unit) : RecyclerView.ViewHolder(b.root) {
    lateinit var note: Note
    fun onBind(note: Note) {
        this.note = note
        setContent()
        setClickListener()
    }

    fun setContent() {
        b.noteContent.text = note.content
    }

    fun setClickListener() {
        b.deleteNoteBtn.setOnClickListener {
            popConfirmation(b.deleteNoteBtn, ::deleteNote)
        }

        b.root.setOnClickListener {
            takeNoteFunc(note)
        }
    }

    private fun deleteNote(confirmation: Boolean) {
        if(confirmation) {
            FireNotes.delete(note, b.root.context)
        }
    }
}