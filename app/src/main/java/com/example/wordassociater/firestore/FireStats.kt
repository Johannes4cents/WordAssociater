package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.utils.Helper

object FireStats {
    fun getStoryPartId(): Long {
        val number = FireLists.stats!!.storyPartNumber + 1
        FireLists.fireStats.update("storyPartNumber", number)
        return number
    }

    fun getProseId(): Long {
        val number = FireLists.stats!!.proseNumber + 1
        FireLists.fireStats.update("proseNumber", number)
        return number
    }

    fun getNuwId(): Long {
        val number = FireLists.stats!!.nuwNumber + 1
        FireLists.fireStats.update("nuwNumber", number)
        return number
    }

    fun getDramaId(): Long {
        val number = FireLists.stats!!.dramaNumber + 1
        FireLists.fireStats.update("dramaNumber", number)
        return number
    }

    fun getSphereId(): Long {
        val number = FireLists.stats!!.sphereNumber + 1
        FireLists.fireStats.update("sphereNumber", number)
        return number
    }

    fun getWordCatId(): Long {
        val number = FireLists.stats!!.wordCatNumber + 1
        FireLists.fireStats.update("wordCatNumber", number)
        return number
    }

    fun getCharConnectId() : Long {
        val number = FireLists.stats!!.connectId + 1
        FireLists.fireStats.update("connectId", number)
        return number
    }

    fun getCharId() : Long {
        val number = FireLists.stats!!.charNumber + 1
        FireLists.fireStats.update("charNumber", number)
        return number
    }

    fun getNoteId(): Long {
        val number = FireLists.stats!!.noteNumber + 1
        FireLists.fireStats.update("noteNumber", number)
        return number
    }

    fun getDialogueId(): Long {
        val number = FireLists.stats!!.dialogueNumber + 1
        FireLists.fireStats.update("dialogueNumber", number)
        return number
    }

    fun getBubbleId(context: Context?): Long {
        val number = FireLists.stats!!.dialogueNumber + 1
        FireLists.fireStats.update("dialogueNumber", number).addOnSuccessListener {
            if(context != null) Helper.toast("bubbleNumber increased", context)
        }
        return number
    }

    fun getWordId(): Long {
        val number = FireLists.stats!!.wordNumber + 1
        FireLists.fireStats.update("wordNumber", number)
        return number
    }

}