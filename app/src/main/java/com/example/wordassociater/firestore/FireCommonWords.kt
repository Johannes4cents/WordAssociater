package com.example.wordassociater.firestore

import com.example.wordassociater.utils.CommonWord
import com.example.wordassociater.utils.Language

object FireCommonWords {
    val languageList = listOf(Language.German, Language.English)
    fun add(commonWord: CommonWord) {
        FireLists.getCommonWordsCollectionRef(commonWord.language).document(commonWord.text).set(commonWord)
    }

    fun delete(word: String, language: Language) {
        FireLists.getCommonWordsCollectionRef(language).document(word).delete()
    }
}