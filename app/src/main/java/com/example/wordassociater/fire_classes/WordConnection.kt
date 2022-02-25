package com.example.wordassociater.fire_classes

import android.util.Log
import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireWordConnections
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

data class WordConnection(
        var id: Long = 0,
        var wordsList: MutableList<Long> = mutableListOf(),
        var storyPart: Long = 0) {

    @Exclude
    fun getWords(): List<Word> {
        val wList = mutableListOf<Word>()
        val notFound = mutableListOf<Long>()
        for(id in wordsList) {
            val word = Main.getWord(id)
            if(word != null) wList.add(word)
            else notFound.add(id)
        }

        if(notFound.isNotEmpty() && wList.isNotEmpty()) wList[0].decreaseWordUsed()

        return wList

    }

    companion object {
        val idList = mutableListOf<Long>()

        fun getId(): Long {
            var id: Long = 1
            while(idList.contains(id) || id < 2) {
                id = (0..100000000).random().toLong()
            }
            return id
        }

        fun connect(storyPart: StoryPart) {
            val allWords = storyPart.getWordsAsStory()
            for(word in allWords) {
                for(w in allWords) {
                    // checks if connection already exists
                    val wConnectionList = w.getWordConnections()
                    var alreadyConnected = false
                    for(connection in wConnectionList) {
                        if(connection.storyPart == storyPart.id && connection.wordsList.contains(word.id)) {
                            alreadyConnected = true
                            break
                        }
                        else {
                            val newConnection = WordConnection(id = getId(), storyPart = storyPart.id)
                            newConnection.wordsList.add(w.id)
                            newConnection.wordsList.add(word.id)
                            w.wordConnectionsList.add(newConnection.id)
                            word.wordConnectionsList.add(newConnection.id)
                            FireWordConnections.add(newConnection, null)
                            FireWords.update(w.id, "wordConnectionsList", w.wordConnectionsList)
                            FireWords.update(word.id, "wordConnectionsList", w.wordConnectionsList)
                        }
                    }
                }
            }
        }

        fun disconnect(word:Word, storyPartId: Long) {
            val affectedConnections = mutableListOf<WordConnection>()
            Log.i("deselectTest", "word connectionCounter : ${word.wordConnectionsList.count()} | word is : ${word.text} | word.id is ${word.id} ")
            for (wc in word.getWordConnections()) {
                Log.i("deselectTest", "word connection : ${wc.wordsList} ")
                if(wc.storyPart == storyPartId) {
                    affectedConnections.add(wc)
                }
            }
            for(wc in affectedConnections) {
                val connectedWord = Main.getWord(wc.wordsList)
                if(connectedWord != null) {
                    connectedWord.wordConnectionsList.remove(wc.id)
                    FireWords.update(connectedWord.id, "wordConnectionsList", connectedWord.wordConnectionsList)
                }
            }
            for(wc in affectedConnections) {
                word.wordConnectionsList.remove(wc.id)
                FireWords.update(word.id, "wordConnectionsList", word.wordConnectionsList)
                FireWordConnections.delete(wc.id)
            }
            FireWords.update(word.id, "wordConnectionsList", word.wordConnectionsList)
        }
    }
}