package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.google.firebase.firestore.Exclude

data class Bubble(
        var id: Long = 0,
        var content: String = "",
        var index: Int = 0,
        var character: Long = 0,
        var color: Color = Color.Green,
        var dialogue: Long = 0, ) {

    var isLeft: Boolean = true

    enum class Color {Green, Blue, Yellow, Orange}

    @Exclude
    var markedForDelete = false

    @Exclude
    var isHeader = false

    @Exclude
    var deleted = false

    @Exclude
    fun getDialogue(): Dialogue? {
        return Main.getDialogue(dialogue)
    }

    @Exclude
    fun getCharacter(): Character? {
        return Main.getCharacter(character)
    }

    companion object {

        fun toIdList(bubbleList: List<Bubble>): MutableList<Long> {
            var idList = mutableListOf<Long>()
            for(bubble in bubbleList) {
                idList.add(bubble.id)
            }
            return idList
        }

        fun getColor(bubbleColor: Bubble.Color): Int {
            return when(bubbleColor) {
                Bubble.Color.Green -> R.color.bubbleGreen
                Bubble.Color.Blue -> R.color.bubbleBlue
                Bubble.Color.Yellow -> R.color.bubbleYellow
                Bubble.Color.Orange -> R.color.bubbleOrange
            }
        }

        fun getAddBubbleBgNoDots(bubbleColor: Color): Int {
            return when(bubbleColor) {
                Bubble.Color.Green -> R.drawable.btn_add_bubble_green_no_dots
                Bubble.Color.Blue -> R.drawable.btn_add_bubble_blue_no_dots
                Bubble.Color.Yellow -> R.drawable.btn_add_bubble_yellow_no_dots
                Bubble.Color.Orange -> R.drawable.btn_add_bubble_orange_no_dots
            }
        }

        fun getAddBubbleBg(bubbleColor: Color): Int {
            return when(bubbleColor) {
                Bubble.Color.Green -> R.drawable.btn_add_bubble_green
                Bubble.Color.Blue -> R.drawable.btn_add_bubble_blue
                Bubble.Color.Yellow -> R.drawable.btn_add_bubble_yellow
                Bubble.Color.Orange -> R.drawable.btn_add_bubble_orange
            }
        }
    }

}