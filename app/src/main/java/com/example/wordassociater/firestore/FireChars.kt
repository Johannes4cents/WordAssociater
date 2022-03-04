package com.example.wordassociater.firestore

import android.content.Context
import android.widget.Toast
import com.example.wordassociater.fire_classes.Character

object FireChars {
    fun add(character: Character, context: Context?) {
        FireLists.characterList.document(character.id.toString()).set(character).addOnSuccessListener {
            if(context != null) Toast.makeText(context, "New Character added", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            if(context != null) Toast.makeText(context, "Failed to \n upload", Toast.LENGTH_SHORT).show()
        }
    }

    fun delete(id: Long) {
        FireLists.characterList.document(id.toString()).delete()
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.characterList.document(id.toString()).update(fieldName, value)
    }

}