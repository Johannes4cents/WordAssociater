package com.example.wordassociater.firestore

import android.content.Context
import android.widget.Toast
import com.example.wordassociater.fire_classes.Dialogue

object FireDialogue {
    fun add(dialogue: Dialogue, context: Context?) {
        FireLists.dialogueList.document(dialogue.id.toString()).set(dialogue).addOnSuccessListener {
            Toast.makeText(context, "Dialogue saved", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(context, "Failed to \n upload", Toast.LENGTH_SHORT).show()
        }
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.dialogueList.document(id.toString()).update(fieldName, value)
    }

    fun delete(id: Long) {
        FireLists.dialogueList.document(id.toString()).delete()
    }
}