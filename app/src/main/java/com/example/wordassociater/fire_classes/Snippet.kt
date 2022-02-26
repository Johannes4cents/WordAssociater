package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireChars
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.utils.Drama
import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

data class Snippet(override var content: String = "",
                   override var id: Long = 0,
                   override var wordList: MutableList<Long> = mutableListOf(),
                   var header: String = "",
                   var connectedSnippetsList: MutableList<Long> = mutableListOf(),
                   var layer: Int = 1,
                   override var characterList: MutableList<Long> = mutableListOf(),
                   override var nuwList: MutableList<Long> = mutableListOf(),
                   override var storyLineList: MutableList<Long> = mutableListOf(),
                   var drama: Drama = Drama.None,
): StoryPart(id, content, wordList, characterList, nuwList, storyLineList) {
    @Exclude
    var recyclerHeader = false

    @Exclude
    fun getCharacters(): List<Character> {
        val chars = mutableListOf<Character>()
        val notFoundList = mutableListOf<Long>()
        for(id in characterList) {
            val char = Main.characterList.value?.find { c -> c.id == id }
            if(char != null) chars.add(char)
            else notFoundList.add(id)
        }

        for(id in notFoundList) {
            characterList.remove(id)
            FireSnippets.update(id, "characterList", characterList)
        }
        return chars
    }

    @Exclude
    fun getNuws(): List<Nuw> {
        val nuws = mutableListOf<Nuw>()
        val notFoundList = mutableListOf<Long>()
        for(id in nuwList) {
            val nuw = Main.getNuwById(id)
            if(nuw != null) nuws.add(nuw)
            else notFoundList.add(id)
        }

        for(id in notFoundList) {
            nuwList.remove(id)
            FireSnippets.update(this.id, "nuwList", nuwList)
        }
        return nuws
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
            FireSnippets.update(id, "wordList", wordList)
        }
        return words
    }

    @Exclude
    fun getConnectedSnippets(): List<Snippet> {
        val snipList = mutableListOf<Snippet>()
        val notFound = mutableListOf<Long>()
        for(id in connectedSnippetsList) {
            val snip = Main.getSnippet(id)
            if(snip != null) snipList.add(snip)
            else notFound.add(id)
        }

        for(id in notFound) {
            connectedSnippetsList.remove(id)
            FireSnippets.update(id, "connectedSnippetsList", connectedSnippetsList)
        }
        return snipList
    }

    @Exclude
    fun delete() {
        for(w in getWords()) {
            w.used -= 1
            w.snippetsList.remove(id)
            FireWords.update(w.id, "used", w.used)
            FireWords.update(w.id, "snippetsList", w.snippetsList)
        }

        for(c in getCharacters()) {
            c.snippetsList.remove(id)
            FireChars.update(c.id, "snippetsList", c.snippetsList)
        }

        for(snippet in getConnectedSnippets()) {
            snippet.connectedSnippetsList.remove(id)
            FireSnippets.update(snippet.id, "connectedSnippets", snippet.connectedSnippetsList)
        }

        FireSnippets.delete(id)
    }

    @Exclude
    fun copyMe(): Snippet {
        val newSnippet = Snippet()
        newSnippet.connectedSnippetsList = connectedSnippetsList.toMutableList()
        newSnippet.drama = drama
        newSnippet.characterList = characterList.toMutableList()
        newSnippet.recyclerHeader = recyclerHeader
        newSnippet.id = 999999999999
        newSnippet.content = content
        newSnippet.nuwList = nuwList.toMutableList()
        newSnippet.wordList = wordList.toMutableList()

        return newSnippet
    }

}