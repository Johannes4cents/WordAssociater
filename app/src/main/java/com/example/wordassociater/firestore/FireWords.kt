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

    fun increaseWordUse(word: Word) {
        val collectionReference = FireLists.getWordCollectionRef(word.type)
        collectionReference.document(word.id).set(word)
    }

    fun update(word: Word,
               imgUrl: String? = null,
               text: String? = null,
               snippetsList : MutableList<Long>? = null,
               strainsList : MutableList<Long>? = null,
               cat: Word.Cat? = null
    ) {
        val collectionReference = FireLists.getWordCollectionRef(word.type)
        if(imgUrl != null) collectionReference.document(word.id).update("imgUrl", imgUrl)
        if(text != null) collectionReference.document(word.id).update("text", text)
        if(snippetsList != null) collectionReference.document(word.id).update("strainsList", imgUrl)
        if(strainsList != null) collectionReference.document(word.id).update("snippetsList", imgUrl)
        if(cat != null) collectionReference.document(word.id).update("cat", cat)
    }

    fun <T>update(type: Word.Type, id: Long, fieldName: String, value: T) {
        val collectionReference = FireLists.getWordCollectionRef(type)
        collectionReference.document(id.toString()).update(fieldName, value)
    }

}