package com.example.wordassociater.firestore

import android.util.Log
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.Helper

object FireWords {
    fun add(newWord: Word) {
        val collectionReference = FireLists.getCollectionRef(newWord.type)
        val list = Helper.getWordList(newWord.type)
        collectionReference.document(newWord.id).set(newWord)
        list.add(newWord)
    }

    fun delete(word: Word) {
        Log.i("WordId", "deletedWord is ${word.id}")
        val collectionReference = FireLists.getCollectionRef(word.type)
        collectionReference.document(word.id).delete()
    }

    fun increaseWordUse(word: Word) {
        val collectionReference = FireLists.getCollectionRef(word.type)
        collectionReference.document(word.id).set(word)
    }

    fun update(word: Word,
               imgUrl: String? = null,
               text: String? = null,
               snippetsList : MutableList<Snippet>? = null,
               strainsList : MutableList<Strain>? = null
    ) {
        val collectionReference = FireLists.getCollectionRef(word.type)
        if(imgUrl != null) collectionReference.document(word.id).update("imgUrl", imgUrl)
        if(text != null) collectionReference.document(word.id).update("text", text)
        if(snippetsList != null) collectionReference.document(word.id).update("strainsList", imgUrl)
        if(strainsList != null) collectionReference.document(word.id).update("snippetsList", imgUrl)
    }
}