package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.words.WordLinear

object FireWords {
    fun add(newWord: Word, context: Context? = null) {
        val collectionReference = FireLists.getWordCollectionRef(newWord.type)
        val list = WordLinear.getWordList(newWord.type)
        collectionReference.document(newWord.id).set(newWord).addOnSuccessListener {
            if(context != null) Helper.toast("Word saved to the database", context)
        }
        list.add(newWord)
    }

    fun delete(word: Word) {
        val collectionReference = FireLists.getWordCollectionRef(word.type)
        collectionReference.document(word.id).delete()
    }

    fun update(type: Word.Type, id: String, fieldName: String, value: Any) {
        val collectionReference = FireLists.getWordCollectionRef(type)
        collectionReference.document(id).update(fieldName, value)
    }

}