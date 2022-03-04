package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.firestore.FireWordCats
import com.example.wordassociater.firestore.FireWords
import com.google.firebase.firestore.Exclude
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class WordCat(
        val id: Long = 0,
        var name: String = "", ) {
    var color: Color = Color.Blue
    var wordList: MutableList<Long> = mutableListOf()
    var active: Boolean = true
    var type: Type = Type.Other

    @Exclude
    var isHeader = false

    @Exclude
    var isSelected = false

    @Exclude
    var used: Int = 0

    enum class Color {Pink, Blue, Brown, Green, Purple, Grey, Teal, Orange, Red, Character, Location, Undefined, Event, Item, Black, DarkGreen, DarkBlue}
    enum class Type {Location, Any, Event, Character, Item, Other}

    @Exclude
    fun countUsed() {
        used = wordList.count()
    }

    @Exclude
    fun delete() {
        CoroutineScope(Dispatchers.IO).launch {
            for(w in wordList) {
                val word = Main.getWord(w)
                if(word != null) {
                    word.cats.remove(id)
                    if(word.cats.isEmpty()) {

                            word.cats.add(0)
                            val any = Main.getWordCat(0)
                            any!!.wordList.add(word.id)
                            FireWordCats.update(any.id, "wordList", any.wordList)


                    }
                    FireWords.update(word.id, "cats", word.cats)

                }
            }
            FireWordCats.delete(id)
        }
    }
    @Exclude
    fun getBg(): Int {
        return when(color) {
            Color.Pink -> R.drawable.wordcat_bg_pink
            Color.Blue -> R.drawable.wordcat_bg_blue
            Color.Brown -> R.drawable.wordcat_bg_brown
            Color.Green -> R.drawable.wordcat_bg_green
            Color.Purple -> R.drawable.wordcat_bg_purple
            Color.Grey -> R.drawable.wordcat_bg_grey
            Color.Teal -> R.drawable.wordcat_bg_teal
            Color.Orange -> R.drawable.wordcat_bg_orange
            Color.Red -> R.drawable.wordcat_bg_red
            Color.Character -> R.drawable.wordcat_bg_character
            Color.Location -> R.drawable.wordcat_bg_location
            Color.Undefined -> R.drawable.wordcat_bg_undefined
            Color.Event -> R.drawable.wordcat_bg_event
            Color.Item -> R.drawable.wordcat_bg_item
            Color.Black -> R.drawable.wordcat_bg_black
            Color.DarkGreen -> R.drawable.wordcat_bg_dark_green
            Color.DarkBlue -> R.drawable.wordcat_bg_dark_blue
        }
    }

}