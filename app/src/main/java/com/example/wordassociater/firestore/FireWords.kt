package com.example.wordassociater.firestore

import android.util.Log
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.words.WordLinear

object FireWords {
    fun add(newWord: Word) {
        val collectionReference = FireLists.getWordCollectionRef(newWord.type)
        val list = WordLinear.getWordList(newWord.type)
        collectionReference.document(newWord.id).set(newWord)
        list.add(newWord)
    }

    fun delete(word: Word) {
        Log.i("WordId", "deletedWord is ${word.id} | word type is ${word.type} ")
        val collectionReference = FireLists.getWordCollectionRef(word.type)
        collectionReference.document(word.id).delete()
    }

    fun <T>update(type: Word.Type, id: String, fieldName: String, value: T) {
        val collectionReference = FireLists.getWordCollectionRef(type)
        collectionReference.document(id.toString()).update(fieldName, value)
    }

}