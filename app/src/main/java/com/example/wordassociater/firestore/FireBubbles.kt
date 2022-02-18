package com.example.wordassociater.firestore

import android.content.Context
import android.widget.Toast
import com.example.wordassociater.fire_classes.Bubble

object FireBubbles {
    fun add(bubble: Bubble, context: Context?) {
        FireLists.dialogueList.document(bubble.id.toString()).set(bubble).addOnSuccessListener {
            Toast.makeText(context, "Snippet saved", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(context, "Failed to \n upload", Toast.LENGTH_SHORT).show()
        }
    }
}