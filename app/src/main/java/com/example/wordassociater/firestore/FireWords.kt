package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.fire_classes.SnippetPart
import com.example.wordassociater.fire_classes.StoryPart
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.Helper

object FireWords {
    fun add(word: Word, context: Context? = null, snippetPart: SnippetPart? = null, storyPart: StoryPart? = null) {
        FireLists.wordsList.document(word.id.toString()).set(word).addOnSuccessListener {
            if(context != null) Helper.toast("${word.text} successfully added to the Database", context)
            if(snippetPart != null) {
                snippetPart.wordList.add(word.id)
                snippetPart.getFullWordsList()
            }

            if(storyPart != null) {
                storyPart.wordList.add(word.id)
                storyPart.getFullWordsList()
            }
        }
    }

    fun delete(id: Long) {
        FireLists.wordsList.document(id.toString()).delete()
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.wordsList.document(id.toString()).update(fieldName, value)
    }

}