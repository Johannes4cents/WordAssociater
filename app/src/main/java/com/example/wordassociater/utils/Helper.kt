package com.example.wordassociater.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.start_fragment.Word
import com.example.wordassociater.start_fragment.WordLinear
import com.example.wordassociater.strain_edit_fragment.Strain
import java.util.*

object Helper {

    fun getIMM(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    fun setWords(wordsList: MutableList<Word>, v: TextView) {
        var wordsString = ""
        for(w: Word in wordsList) {
            wordsString += "${w.text}, "
            if(wordsString.length > 25) wordsString += "\n"
        }
        v.text = wordsString
    }

    fun deselectWords() {
        for(w in WordLinear.selectedWords) {
            w.selected = false
            val index = WordLinear.wordList.indexOf(w)
            WordLinear.wordList.remove(w)
            WordLinear.wordList.add(index, w)
        }
        WordLinear.selectedWords.clear()
        WordLinear.wordListTriger.value = Unit
    }

    fun checkIfWordExists(word: Word, context: Context): Boolean {
        val wordList = getWordList(word.type)
        var exists = false
        for(w in wordList) {
            if (w.text.toLowerCase(Locale.ROOT).replace("\\s".toRegex(), "") == word.text.toLowerCase(Locale.ROOT).replace("\\s".toRegex(), "")) {
                Toast.makeText(context, "Word ${word.text} \n already exists", Toast.LENGTH_SHORT).show()
                exists = true
                break
            }
        }
        return exists
    }

    fun getWordList(type: Word.Type): MutableList<Word> {
        return when(type) {
            Word.Type.Adjective -> WordLinear.adjectivesList
            Word.Type.Person -> WordLinear.personsList
            Word.Type.Place -> WordLinear.placesList
            Word.Type.Action -> WordLinear.actionsList
            Word.Type.Object -> WordLinear.objectsList
            Word.Type.CHARACTER -> WordLinear.characterList
            Word.Type.NONE -> WordLinear.objectsList
        }
    }

    private fun wordToList(word: Word): List<String> {
        return word.text.split("\\s".toRegex())
    }

    fun getOrFilteredStoryPartList(filterWords: List<String>, searchList: List<StoryPart>): MutableList<StoryPart> {
        val submitList = mutableListOf<StoryPart>()
        for(storyPart in searchList) {
            var match = false
            val wordContentList = storyPart.content.split("\\s".toRegex())
            for(string in wordContentList) {
                val copiedFilterWords = filterWords.toList()
                val strippedW = stripWord(string)
                for(i in 0 until copiedFilterWords.count()) {
                    if(strippedW.startsWith(filterWords[i])) match = true ; break
                }
            }
            if(!match) {
                for(w in storyPart.words) {
                    var destructedWord = wordToList(w)
                    for(str in destructedWord) {
                        if(filterWords.contains(stripWord(str))) match = true ; break
                    }
                }
            }

            if(!match && storyPart is Strain) {
                var stringList = (storyPart as Strain).header.split("\\s".toRegex())
                for(string in stringList) {

                }

            }
            if(match) submitList.add(storyPart)
        }
        return submitList
    }

    fun getStrainsById(idList : List<String>): List<Strain> {
        val foundStrains = mutableListOf<Strain>()
        for(id in idList) {
            val strain = Main.strainsList.value?.find { s -> s.id == id }
            if(strain != null ) foundStrains.add(strain)
        }
        return foundStrains
    }

    fun toast(text: String, context: Context) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun stripWord(word: String): String {
        return word.toLowerCase()
                .replace("/", "")
                .replace("!", "")
                .replace("/", "")
                .replace("(", "")
                .replace(")", "")
                .replace(".", "")
                .replace(",", "")
                .replace(" ", "")
                .replace("%", "")
                .replace("?", "")
                .replace("'", "")
                .replace("&", "")
                .replace("%", "")
    }

    fun setWordsToMultipleLines(list: MutableList<Word>): String {
        var wordList = ""
        var wordRow = ""
        for(w: Word in list) {
            if(wordRow.length + w.text.length < 25) {
                wordRow += "${w.text}, "
            }
            else {
                wordRow += "\n"
                wordList += wordRow
                wordRow = "${w.text} "
            }
        }
        wordList += wordRow
        return wordList
    }

    fun getWordBg(type: Word.Type): Int {
        return when(type) {
            Word.Type.Adjective -> R.drawable.word_bg_adjective
            Word.Type.Person -> R.drawable.word_bg_person
            Word.Type.Place -> R.drawable.word_bg_place
            Word.Type.Action -> R.drawable.word_bg_action
            Word.Type.Object -> R.drawable.word_bg_objects
            Word.Type.CHARACTER -> R.drawable.word_bg_hero
            Word.Type.NONE -> R.drawable.word_bg_objects
        }
    }
}