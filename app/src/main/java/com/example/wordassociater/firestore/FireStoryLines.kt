package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.fire_classes.SnippetPart
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.utils.Helper

object FireStoryLines {
    fun add(storyLine: StoryLine, context: Context?, snippetPart: SnippetPart? = null) {
        FireLists.storyLineList.document(storyLine.id.toString()).set(storyLine).addOnSuccessListener {
            if(context != null) Helper.toast("StoryLine added to the Database", context)
            if(snippetPart != null) {
                snippetPart.takeStoryLine(storyLine)
                snippetPart.updateStoryLines()
            }
        }
    }

    fun delete(id: Long) {
        FireLists.storyLineList.document(id.toString()).delete()
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.storyLineList.document(id.toString()).update(fieldName, value)
    }
}