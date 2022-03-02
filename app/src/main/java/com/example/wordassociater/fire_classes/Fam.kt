package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.firestore.FireFams
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.utils.CommonWord
import com.example.wordassociater.utils.Language
import com.google.firebase.firestore.Exclude

data class Fam(
        val id: Long = 0,
        var text: String = "",
        ) {
    var wordClass: Class = Class.Noun
    var commonWord: CommonWord.Type = CommonWord.Type.Uncommon
    var alreadyWord: Boolean = false
    var word : Long = 0
    var main: Boolean = false
    var firstOpen = false

    enum class Class(val image: Int) { Verb(R.drawable.word_class_verb), Adjective(R.drawable.word_class_adjective), Noun(R.drawable.word_class_noun) }
    @Exclude
    var isHeader = false



    @Exclude
    fun checkIfCommon(): CommonWord? {
        val cWord = Main.getCommonWord(Language.German, text)
        if(cWord != null) commonWord = cWord.type
        return cWord
    }

    @Exclude
    fun checkIfWord(): Word? {
        val word = Main.getWordByText(text)
        if(word != null) {
            commonWord = CommonWord.Type.Uncommon
            alreadyWord = true
        }
        return word
    }

    @Exclude
    fun delete() {
        val word = Main.getWord(word)
        word!!.famList.remove(id)
        if(main) {
            val newMainFam = Main.getFam(word.famList[0])
            newMainFam!!.main = true
            FireFams.update(newMainFam.id, "main", newMainFam.main)
        }
        FireWords.update(word.id, "famList", word.famList)
        FireFams.delete(id)
    }

    @Exclude
    fun getClassImage(): Int {
        return when(wordClass) {
            Class.Verb -> R.drawable.word_class_verb
            Class.Adjective -> R.drawable.word_class_adjective
            Class.Noun -> R.drawable.word_class_noun
        }
    }
}