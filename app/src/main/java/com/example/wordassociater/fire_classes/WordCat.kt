package com.example.wordassociater.fire_classes

import com.example.wordassociater.R
import com.google.firebase.firestore.Exclude

class WordCat(
        val id: Long = 0,
        val name: String = "",
        val color: Color = Color.Blue
) {
    enum class Color {Pink, Blue, Brown, Green, Purple, Grey}

    @Exclude
    fun getColor(type: Color): Int {
        return when(type) {
            Color.Pink -> R.color.wordPink
            Color.Blue -> R.color.wordBlue
            Color.Brown -> R.color.wordBrown
            Color.Green -> R.color.green
            Color.Purple -> R.color.wordPurple
            Color.Grey -> R.color.wordGrey
        }
    }
}