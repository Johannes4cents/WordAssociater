package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.firestore.FireWordCats
import com.example.wordassociater.firestore.FireWords
import com.google.firebase.firestore.Exclude

data class WordCat(
        val id: Long = 0,
        val name: String = "") {
    var color: Color = Color.Blue
    var wordList: MutableList<Long> = mutableListOf()
    val importance = 5
    var active: Boolean = true
    var type: Type = Type.Other

    @Exclude
    var isHeader = false

    @Exclude
    var isSelected = false

    @Exclude
    var used: Int = 0

    enum class Color {Pink, Blue, Brown, Green, Purple, Grey, Teal, Orange, Red, Character, Location, Undefined, Event}
    enum class Type {Location, Any, Event, Character, Other}

    @Exclude
    fun countUsed() {
        used = wordList.count()
    }

    @Exclude
    fun delete() {
        for(w in wordList) {
            val word = Main.getWord(w)
            if(word != null) {
                word.cats.remove(id)
                if(word.cats.isEmpty()) word.cats.add(0)
                FireWords.update(word.id, "cats", word.cats)
            }
        }
        FireWordCats.delete(id)
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
        }
    }

}