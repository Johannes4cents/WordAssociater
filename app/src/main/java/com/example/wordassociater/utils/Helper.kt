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
import com.example.wordassociater.StartFragment
import com.example.wordassociater.fire_classes.Word
import java.util.*

object Helper {

    fun getIMM(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    fun <T>getNewList(list: MutableList<T> ): List<T> {
        val newList = mutableListOf<T>()
        for(item in list) {
            newList.add(item)
        }
        return newList
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
        val wordList = Main.wordsList.value!!
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

    fun getWords(wordList: List<Long>): MutableList<Word> {
        val words = mutableListOf<Word>()
        for(id in wordList) {
            words.add(Main.getWord(id)!!)
        }
        return words
    }


    fun checkIfWordInRightSphere(word: Word): Boolean {
        var inRightSphere = false
        if(StartFragment.selectedSphereList.value != null) {
            for(sphere in word.getSphereList()) {
                if(StartFragment.selectedSphereList.value!!.contains(sphere)) inRightSphere = true
            }
        }

        return inRightSphere
    }

    fun <T>getResubmitList(item: T, itemList: List<T>): MutableList<T>? {
        val itemFound = itemList.find { i -> i == item }
        val newList = itemList.toMutableList()
        val index = newList.indexOf(item)
        newList.remove(item)
        newList.add(index, item)
        return newList
    }

    fun getDramaImage(type: Drama): Int {
        return when(type) {
            Drama.Conflict -> R.drawable.icon_conflict
            Drama.Twist -> R.drawable.icon_twist
            Drama.Plan -> R.drawable.icon_plan
            Drama.Motivation -> R.drawable.icon_motivation
            Drama.Goal -> R.drawable.icon_goal
            Drama.Problem -> R.drawable.icon_problem
            Drama.Solution -> R.drawable.icon_solution
            Drama.Hurdle -> R.drawable.icon_hurdle
            Drama.None -> R.drawable.icon_dramaturgy
            Drama.Comedy -> R.drawable.icon_comedy
        }
    }

    fun getPopUp(layout: View, fromWhere: View, width: Int?, height: Int?, fromMiddle: Boolean = false): PopupWindow {
        val popWindow = PopupWindow(fromWhere.context)
        if(height != null) popWindow.height = height
        if(width != null )popWindow.width = width
        popWindow.isOutsideTouchable = true
        popWindow.isFocusable = true
        popWindow.contentView = layout
        if(!fromMiddle) popWindow.showAsDropDown(fromWhere)
        else {
            popWindow.showAtLocation(fromWhere, Gravity.TOP,0, 0)
        }
        popWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        return popWindow
    }

    fun takeFocus(view:View, context: Context) {
        view.visibility = View.VISIBLE
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.requestFocus()
        getIMM(context).showSoftInput(view, 0)
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

    fun contentToWordList(content: String): List<String> {
        var stringList = content.split("\\s".toRegex())
        var strippedString = mutableListOf<String>()
        for(string in stringList) {
            strippedString.add(stripWord(string))
        }
        return strippedString
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

}