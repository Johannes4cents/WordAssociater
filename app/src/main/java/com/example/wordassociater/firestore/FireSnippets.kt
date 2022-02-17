package com.example.wordassociater.firestore

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.wordassociater.fire_classes.Snippet

object FireSnippets {
    fun delete(snippet: Snippet) {
        FireLists.snippetsList.document(snippet.id.toString()).delete()
    }

    fun add(newSnippet: Snippet, context: Context?) {
        FireLists.snippetsList.document(FireLists.stats?.snippetNumber.toString()).set(newSnippet).addOnSuccessListener {
            Toast.makeText(context, "Snippet saved", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(context, "Failed to \n upload", Toast.LENGTH_SHORT).show()
        }
    }

    fun update(snippet: Snippet, context: Context?) {
        Log.i("snippet", "snippet id: ${snippet.id}")
        FireLists.snippetsList.document(snippet.id.toString()).set(snippet).addOnSuccessListener {
            if(context != null) Toast.makeText(context, "Snipped updates", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            if(context != null) Toast.makeText(context, "Failed to update Snippet", Toast.LENGTH_SHORT).show()
        }
    }
}