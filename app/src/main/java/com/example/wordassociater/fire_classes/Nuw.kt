package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.utils.FamCheck
import com.example.wordassociater.utils.Helper
import com.google.firebase.firestore.Exclude

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
                if(text != "Any" && text != "any") {
                        if(checkIfWordExists() == null) {
                                val word = Word()
                                word.id = FireStats.getWordId()
                                word.text = Helper.stripWordLeaveWhiteSpace(text)
                                FireWords.add(word, null)
                                Main.getWordCat(0)?.wordList?.add(word.id)
                                isWord = true
                        }
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
                        // check fams
                        for(fam in w.getFams()) {
                                if(fam.text == Helper.stripWordLeaveWhiteSpace(text)) {
                                        foundWord = w
                                }
                        }
                        // check stems
                        for(stem in w.stems) {
                                if(text.contains(stem, ignoreCase = true) && text != stem && !w.getFamStrings().contains(text)) {
                                        val fam = Fam(FireStats.getFamNumber(), text)
                                        w.famList.add(fam.id)
                                        FireWords.update(w.id, "famList", w.famList)
                                        FamCheck.addFamCheck(stem, w.text)
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