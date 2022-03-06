package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireStems
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.utils.LiveClass
import com.google.firebase.firestore.Exclude

data class Stem(override var id : Long = 0L, override var name: String = "", var word: Long = 0L): LiveClass {
    @Exclude
    override var sortingOrder: Int = id.toInt()
    @Exclude
    override var isAHeader: Boolean = false
    @Exclude
    override var selected: Boolean = false
    @Exclude
    override var image: Long = 0L

    @Exclude
    var isNew : Boolean = false

    @Exclude
    fun delete() {
        val word = Main.getWord(word)!!
        word.stemList.remove(id)
        FireWords.update(word.id, "stemList", word.stemList)

        FireStems.delete(id)
    }

    companion object {
        fun checkIfAlreadyExists(stem: String): Stem? {
            return Main.getStemByString(stem)
        }

        fun checkFamIfInStems(famText: String): Stem? {
            var foundStem: Stem? = null
            for(stem in Main.stemList.value!!) {
                if(famText.contains(stem.name, ignoreCase = true)) {
                    foundStem = stem
                    break
                }
            }
            return foundStem
        }
    }
}