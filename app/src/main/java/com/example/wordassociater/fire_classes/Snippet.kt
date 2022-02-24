package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireChars
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

data class Snippet(override var content: String = "",
                   override var id: Long = 0,
                   override var wordList: MutableList<Long> = mutableListOf(),
                   var connectedSnippets: MutableList<Long> = mutableListOf(),
                   override var characterList: MutableList<Long> = mutableListOf(),
                   override var nuwList: MutableList<Nuw> = mutableListOf(),
                   var drama: Drama.Type = Drama.Type.None,
                   var isStory: Boolean = false): StoryPart(id, content, wordList, characterList, nuwList) {
    @Exclude
    var isHeader = false

    @Exclude
    fun getCharacters(): List<Character> {
        val chars = mutableListOf<Character>()
        for(id in characterList) {
            val char = Main.characterList.value?.find { c -> c.id == id }
            if(char != null) chars.add(char)
        }
        return chars
    }

    @Exclude
    fun connectedSnippetsList(): List<Snippet> {
        val snipList = mutableListOf<Snippet>()
        for(id in connectedSnippets) {
            val snip = Main.getSnippet(id)
            if(snip != null) snipList.add(snip)
        }
        return snipList
    }

    @Exclude
    fun delete() {
        for(w in getWords()) {
            w.used -= 1
            w.snippetsList.remove(id)
            FireWords.update(w.id, "used", w.used)
            FireWords.update(w.id, "snippetsList", w.used)
        }

        for(c in getCharacters()) {
            c.snippetsList.remove(id)
            FireChars.update(c.id, "snippetsList", c.snippetsList)
        }

        for(snippet in connectedSnippetsList()) {
            snippet.connectedSnippets.remove(id)
            FireSnippets.update(snippet.id, "connectedSnippets", snippet.connectedSnippets)
        }

        FireSnippets.delete(id)
    }

    @Exclude
    fun copyMe(): Snippet {
        val newSnippet = Snippet()
        newSnippet.connectedSnippets = connectedSnippets.toMutableList()
        newSnippet.drama = drama
        newSnippet.characterList = characterList.toMutableList()
        newSnippet.isHeader = isHeader
        newSnippet.id = 999999999999
        newSnippet.content = content
        newSnippet.nuwList = nuwList.toMutableList()
        newSnippet.wordList = wordList.toMutableList()
        newSnippet.isStory = isStory

        return newSnippet
    }

}