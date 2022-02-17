package com.example.wordassociater.firestore

import com.example.wordassociater.start_fragment.Word
import com.example.wordassociater.utils.Helper

object FireWords {
    fun add(newWord: Word) {
        val collectionReference = FireLists.getCollectionRef(newWord.type)
        val list = Helper.getWordList(newWord.type)
        collectionReference.document(newWord.id).set(newWord)
        list.add(newWord)
    }

    fun delete(word: Word) {
        val collectionReference = FireLists.getCollectionRef(word.type)
        collectionReference.document(word.id).delete()
    }

    fun increaseWordUse(word: Word) {
        val collectionReference = FireLists.getCollectionRef(word.type)
        word.used += 1
        collectionReference.document(word.id).set(word)
    }

    fun update(word: Word,
        imgUrl: String? = null,
        id: String? = null,
        name: String? = null
    ) {
        if(imgUrl != null) FireLists.fireStrainsList.document(word.id).set(word)
    }
}