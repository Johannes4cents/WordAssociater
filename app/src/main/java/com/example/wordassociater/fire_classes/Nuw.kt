package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireLists
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.SynonymCheck
import com.google.firebase.firestore.Exclude
import java.util.*

data class Nuw(
        var id: Long = 0,
        var text: String = "") {
        var markedAsCommonWord = false
        var usedIn : MutableList<Long> = mutableListOf()
        var usedAmount: Int = 1
        var isWord : Boolean = false

        @Exclude
        var isUsed = false

        @Exclude
        fun upgradeToWord() {
                if(checkIfWordExists() == null) {
                        val word = Word()
                        word.id = FireStats.getWordId()
                        word.text = text
                        FireWords.add(word, null)
                        isWord = true
                }
        }

        @Exclude
        fun checkIfExists(): Nuw {
                val nuw = Main.getNuw(text)
                if(nuw != null) {
                        id = nuw.id
                        usedAmount = nuw.usedAmount
                        usedIn = nuw.usedIn
                        isWord = nuw.isWord
                }
                return this
        }

        fun checkIfWordExists(): Word? {
                var foundWord: Word? = null
                for(w in Main.wordsList.value!!) {
                        if(w.synonyms.contains(text) || Helper.stripWord(w.text).capitalize(Locale.ROOT) == text) {
                                foundWord = w
                                break
                        }

                        // check stems
                        for(stem in w.stems) {
                                if(text.contains(stem)) {
                                        w.synonyms.add(text)
                                        FireWords.update(w.id, "synonyms", w.synonyms)
                                        val sc = SynonymCheck(stem, w.text)
                                        FireLists.addNewSynonymsToInspect(sc)
                                        foundWord = w
                                        break
                                }
                        }
                }
                return foundWord
         }

        companion object {
                val idList = mutableListOf<Long>()
                fun getNuw(text: String): Nuw {
                        var id: Long = 1
                        while(id < 2 || idList.contains(id)) {
                                id = (1..99999999).random().toLong()
                        }
                        val newNuw = Nuw(
                                id=id,
                                text = text)
                        return newNuw.checkIfExists()
                }
        }
}