package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.fire_classes.Word

object FireWords {
    fun add(word: Word, context: Context? = null) {
        FireLists.wordsList.document(word.id.toString()).set(word)
    }

    fun delete(id: Long) {
        FireLists.wordsList.document(id.toString()).delete()
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.wordsList.document(id.toString()).update(fieldName, value)
    }

}