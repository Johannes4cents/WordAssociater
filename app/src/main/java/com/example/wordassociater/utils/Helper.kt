package com.example.wordassociater.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import android.widget.Toast
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.start_fragment.WordLinear
import java.util.*

object Helper {

    fun getIMM(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    fun setWordsToString(wordsList: List<Word>) : String{
        var wordsString = ""
        for(w: Word in wordsList) {
            wordsString += "${w.text}, "
            if(wordsString.length > 25) wordsString += "\n"
        }
        return wordsString
    }


    fun checkIfWordExists(word: Word, context: Context): Boolean {
        val wordList = WordLinear.getWordList(word.type)
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

    private fun wordToList(word: Word): List<String> {
        return word.text.split("\\s".toRegex())
    }

    fun getWords(wordList: List<String>): MutableList<Word> {
        val words = mutableListOf<Word>()
        for(string in wordList) {
            words.add(Main.getWord(string)!!)
        }
        return words
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
                for(w in getWords(storyPart.wordList)) {
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

    fun <T>getResubmitList(item: T, itemList: List<T>): MutableList<T>? {
        val newList = itemList.toMutableList()
        val index = newList.indexOf(item)
        newList.remove(item)
        newList.add(index, item)
        return newList
    }

    fun getPopUp(layout: View, fromWhere: View, width: Int, height: Int): PopupWindow {
        val popWindow = PopupWindow(fromWhere.context)
        popWindow.height = height
        popWindow.width = width
        popWindow.isOutsideTouchable = true
        popWindow.isFocusable = true
        popWindow.contentView = layout
        popWindow.showAtLocation(fromWhere, Gravity.CENTER, 0 , 0)
        popWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        return popWindow
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

    fun setStringToMultipleLines(word: String): String {
        var word = word.split("\\s".toRegex())
        return "${word[0]} \n ${word[1]}"
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