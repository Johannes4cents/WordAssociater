package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

data class Dialogue(
        override var id: Long = 0,
        override var characterList: MutableList<Long> = mutableListOf(),
        override var content: String = "",
        override var nuwList: MutableList<Nuw> = mutableListOf(),
        override var wordList: MutableList<String> = mutableListOf(),
        var currentIndex: Int = 1,
        var drama: Drama.Type = Drama.Type.None,
        var bubbleList: MutableList<Long> = mutableListOf()
): StoryPart(id, content, wordList, characterList, nuwList) {
    @Exclude
    fun getCharacter(): List<Character> {
        val charList = mutableListOf<Character>()
        for(c in characterList) {
            var char = Main.getCharacter(c)
            if(char != null) charList.add(char)
        }
        return charList
    }

    @Exclude
    fun getBubbles(): List<Bubble> {
        val bubbles = mutableListOf<Bubble>()
        for(id in bubbleList) {
            var bubble = Main.getBubble(id)
            if(bubble != null) bubbles .add(bubble)
        }
        return bubbles
    }

    @Exclude
    fun delete() {
        for(w in getWords()) {
            w.used -= 1
            w.dialogueList.remove(id)
            FireWords.update(w.type, w.id, "used", w.used)
            FireWords.update(w.type, w.id, "dialogueList", w.used)
        }
    }
}