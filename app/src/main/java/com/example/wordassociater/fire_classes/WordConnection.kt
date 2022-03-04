package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireWordConnections
import com.example.wordassociater.firestore.FireWords
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

        private fun getId(): Long {
            var id: Long = 1
            while(idList.contains(id) || id < 2) {
                id = (0..999999999).random().toLong()
            }
            return id
        }

        fun connect(storyPart: StoryPart) {
            val allWords = storyPart.getWords()
            var connectionsList = mutableListOf<WordConnection>()

            // check to include already established connections when updating a storyPart
            for(word in allWords) {
                val wcs = word.getWordConnections()
                for(wc in wcs) {
                    if(wc.storyPart == storyPart.id && !connectionsList.contains(wc)) {
                        connectionsList.add(wc)
                    }
                }
            }
            // add connections that aren't already in connectionsList
            for(word in allWords) {
                for(w in allWords) {
                    if(word != w) {
                        val newConnection = WordConnection(getId(), storyPart = storyPart.id)
                        newConnection.wordsList.add(word.id)
                        newConnection.wordsList.add(w.id)
                        var alreadyConnected = false
                        for(wc in connectionsList) {
                            if(wc.wordsList.contains(word.id) && wc.wordsList.contains(w.id)) {
                                alreadyConnected = true
                                break
                            }
                        }
                        if(!alreadyConnected) connectionsList.add(newConnection)
                    }
                }
            }
            // update the words and save the connections in Firestore
            for (word in allWords) {
                for(connection in connectionsList) {
                    if(!word.wordConnectionsList.contains(connection.id) && connection.wordsList.contains(word.id)) {
                        word.wordConnectionsList.add(connection.id)
                        FireWords.update(word.id, "wordConnectionsList", word.wordConnectionsList)
                    }
                    FireWordConnections.add(connection, null)
                }
            }
        }

        fun disconnect(word:Word, storyPartId: Long) {
            val affectedConnections = mutableListOf<WordConnection>()
            // select all WordConnections from that storyPart
            for (wc in word.getWordConnections()) {
                if(wc.storyPart == storyPartId) {
                    affectedConnections.add(wc)
                }
            }
            // remove the word connection in both words of the connection
            for(wc in affectedConnections) {
                val connectedWords = Word.convertIdListToWord(wc.wordsList)
                for(w in connectedWords) {
                    w.wordConnectionsList.remove(wc.id)
                    FireWords.update(w.id, "wordConnectionsList", w.wordConnectionsList)
                }
            }

            // remove the wordConnection from the DB
            for(wc in affectedConnections) {
                FireWordConnections.delete(wc.id)
            }
        }
    }
}