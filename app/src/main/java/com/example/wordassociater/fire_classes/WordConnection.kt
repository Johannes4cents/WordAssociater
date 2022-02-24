package com.example.wordassociater.fire_classes

import android.util.Log
import com.example.wordassociater.Main
import com.example.wordassociater.firestore.FireWordConnections
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

data class WordConnection(
        var id: Long = 0,
        var word: Long = 0,
        var storyPart: Long = 0) {

    @Exclude
    fun getWord(): Word {
        return Main.getWord(word)!!
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

        fun handleWordConnections(storyPart: StoryPart) {
            Log.i("wordConProbs", "${storyPart.wordList.count()}")
            for(word in storyPart.getWords()) {
                for(w in storyPart.getWords()) {
                    if(w != word) {
                        var alreadyConnected = false
                        for(wc in word.wordConnectionsList) {
                            val connection = Main.getWordConnection(wc)
                            if(connection != null) {
                                if(connection.storyPart == storyPart.id) {
                                    alreadyConnected = true
                                    break
                                }
                            }
                        }
                        if(!alreadyConnected) {
                            val wc = WordConnection(id = getId(), word = w.id, storyPart = storyPart.id)
                            FireWordConnections.add(wc, null)
                            word.wordConnectionsList.add(wc.id)
                            Log.i("wordConProbs", "word is ${word.text} connection word is ${w.text}")
                            FireWords.update(word.id, "connections", word.wordConnectionsList)
                        }
                    }
                }
            }
        }
    }
}