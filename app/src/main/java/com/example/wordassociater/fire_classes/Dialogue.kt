package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireBubbles
import com.example.wordassociater.firestore.FireChars
import com.example.wordassociater.firestore.FireDialogue
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.utils.Drama
import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

data class Dialogue(
        override var id: Long = 0,
        override var characterList: MutableList<Long> = mutableListOf(),
        override var content: String = "",
        override var nuwList: MutableList<Long> = mutableListOf(),
        override var wordList: MutableList<Long> = mutableListOf(),
        override var storyLineList: MutableList<StoryLine> = mutableListOf(),
        var currentIndex: Int = 1,
        var drama: Drama = Drama.None,
        var bubbleList: MutableList<Long> = mutableListOf()
): StoryPart(id, content, wordList, characterList, nuwList, storyLineList) {
    @Exclude
    fun getCharacter(): List<Character> {
        val charList = mutableListOf<Character>()
        val notFound = mutableListOf<Long>()
        for(c in characterList) {
            var char = Main.getCharacter(c)
            if(char != null) charList.add(char)
        }

        for(id in notFound) {
            characterList.remove(id)
            FireDialogue.update(id, "characterList", characterList)
        }
        return charList
    }

    @Exclude
    fun getBubbles(): List<Bubble> {
        val bubbles = mutableListOf<Bubble>()
        val notFound = mutableListOf<Long>()
        for(id in bubbleList) {
            val bubble = Main.getBubble(id)
            if(bubble != null) bubbles .add(bubble)
            else notFound.add(id)
        }

        for(id in notFound) {
            bubbleList.remove(id)
            FireDialogue.update(id, "bubbleList", bubbleList)
        }
        return bubbles
    }

    @Exclude
    fun getWords(): MutableList<Word> {
        val words = mutableListOf<Word>()
        val notFound = mutableListOf<Long>()
        for(id in wordList) {
            val found = Main.getWord(id)
            if(found != null) words.add(found)
            else notFound.add(id)
        }

        for(id in notFound) {
            wordList.remove(id)
            FireDialogue.update(id, "wordList", wordList)
        }
        return words
    }

    @Exclude
    fun delete() {
        for(w in getWords()) {
            w.used -= 1
            w.dialogueList.remove(id)
            FireWords.update(w.id, "used", w.used)
            FireWords.update(w.id, "dialogueList", w.used)
        }

        for(c in getCharacter()) {
            c.dialogueList.remove(id)
            FireChars.update(c.id, "dialogueList", c.dialogueList)
        }

        for(b in getBubbles()) {
            FireBubbles.delete(b.id, null)
        }

        FireDialogue.delete(id)
    }

    @Exclude
    fun copyMe(): Dialogue  {
        val clone = Dialogue()
        clone.id = 98799999
        clone.characterList = characterList.toMutableList()
        clone.content = content
        clone.nuwList = nuwList.toMutableList()
        clone.wordList = wordList.toMutableList()
        clone.currentIndex = currentIndex
        clone.drama = drama
        clone.bubbleList = bubbleList.toMutableList()
        return clone
    }
}