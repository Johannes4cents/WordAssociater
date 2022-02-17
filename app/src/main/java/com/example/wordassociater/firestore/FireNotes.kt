package com.example.wordassociater.firestore

import android.content.Context
import android.widget.Toast
import com.example.wordassociater.notes.Note

object FireNotes {
    fun add(note: Note, context: Context?) {
        FireLists.noteList.document(note.id.toString()).set(note).addOnSuccessListener {
            if(context != null) {
                Toast.makeText(context, "Note saved", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to save note", Toast.LENGTH_SHORT).show()
        }
    }

    fun delete(note: Note, context: Context?) {
        FireLists.noteList.document(note.id.toString()).delete().addOnSuccessListener {
            if(context != null) {
                Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            if(context != null) {
                Toast.makeText(context, "couldn't delete Note", Toast.LENGTH_SHORT).show()
            }
        }
    }
}