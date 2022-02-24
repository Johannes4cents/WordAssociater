package com.example.wordassociater.firestore

import android.content.Context
import android.widget.Toast
import com.example.wordassociater.fire_classes.Bubble

object FireBubbles {
    fun add(bubble: Bubble, context: Context?) {
        FireLists.bubbleList.document(bubble.id.toString()).set(bubble).addOnSuccessListener {

        }.addOnFailureListener {

        }
    }

    fun delete(bubble: Bubble, context: Context?) {
        FireLists.bubbleList.document(bubble.id.toString()).delete().addOnSuccessListener {
            if(context != null) Toast.makeText(context, "Bubble deleted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            if(context != null) Toast.makeText(context, "Failed to \n delete bubble", Toast.LENGTH_SHORT).show()
        }
    }
}