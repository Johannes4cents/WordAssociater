package com.example.wordassociater.notes

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderNoteBinding
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.firestore.FireNotes

class NoteHolder(val b : HolderNoteBinding) : RecyclerView.ViewHolder(b.root) {
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
            Pop(b.root.context).confirmationPopUp(b.deleteNoteBtn, ::deleteNote)
        }
    }

    private fun deleteNote(confirmation: Boolean) {
        if(confirmation) {
            FireNotes.delete(note, b.root.context)
        }
    }
}