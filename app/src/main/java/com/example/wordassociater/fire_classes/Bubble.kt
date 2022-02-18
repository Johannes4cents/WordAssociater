package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.google.firebase.firestore.Exclude

data class Bubble(
        var id: Long = 0,
        var content: String = "",
        var index: Int = 0,
        var character: String = "",
        var dialogue: Long = 0, ) {

    @Exclude
    fun getDialogue(): Dialogue? {
        return Main.getDialogue(dialogue)
    }

    @Exclude
    fun getCharacter(): Character? {
        return Main.getCharacter(character)
    }
}