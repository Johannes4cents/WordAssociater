package com.example.wordassociater.firestore

import android.content.Context
import android.widget.Toast
import com.example.wordassociater.fire_classes.Snippet

object FireSnippets {
    fun delete(snippet: Snippet) {
        FireLists.snippetsList.document(snippet.id.toString()).delete()
    }

    fun add(newSnippet: Snippet, context: Context?) {
        FireLists.snippetsList.document(newSnippet.id.toString()).set(newSnippet).addOnSuccessListener {
            Toast.makeText(context, "Snippet saved", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(context, "Failed to \n upload", Toast.LENGTH_SHORT).show()
        }
    }

    fun update(id: Long, field: String, value: Any) {
        FireLists.snippetsList.document(id.toString()).update(field, value)
    }
}