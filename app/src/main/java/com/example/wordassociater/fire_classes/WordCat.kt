package com.example.wordassociater.fire_classes

import com.example.wordassociater.R
import com.google.firebase.firestore.Exclude

data class WordCat(
        val id: Long = 0,
        val name: String = "",
        var color: Color = Color.Blue
) {
    var active: Boolean = true

    @Exclude
    var isHeader = false

    enum class Color {Pink, Blue, Brown, Green, Purple, Grey, Teal, Orange, Red}

    @Exclude
    fun getColor(): Int {
        return when(color) {
            Color.Pink -> R.color.wordPink
            Color.Blue -> R.color.wordBlue
            Color.Brown -> R.color.wordBrown
            Color.Green -> R.color.green
            Color.Purple -> R.color.wordPurple
            Color.Grey -> R.color.wordGrey
            Color.Teal -> R.color.wordTeal
            Color.Orange -> R.color.wordOrange
            Color.Red -> R.color.wordRed
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
        }
    }

    @Exclude
    fun getPlusSign(): Int {
        return when(color) {
            Color.Pink -> R.drawable.wordcat_plus_pink
            Color.Blue -> R.drawable.wordcat_plus_blue
            Color.Brown -> R.drawable.wordcat_plus_brown
            Color.Green -> R.drawable.wordcat_plus_green
            Color.Purple -> R.drawable.wordcat_plus_purple
            Color.Grey -> R.drawable.wordcat_plus_grey
            Color.Teal -> R.drawable.wordcat_plus_teal
            Color.Orange -> R.drawable.wordcat_plus_orange
            Color.Red -> R.drawable.wordcat_plus_red
        }
    }
}