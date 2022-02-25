package com.example.wordassociater.fire_classes

import com.example.wordassociater.Main
import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

data class Nuw(
        var id: Long = 0,
        var text: String = "",
        var usedIn : MutableList<StoryPart> = mutableListOf(),
        var usedAmount: Int = 0,
        var isWord : Boolean = false
        ) {

        @Exclude
        var isUsed = false

        @Exclude
        fun checkIfExist() {
                val nuw = Main.getNuw(text)
                if(nuw != null) {
                        id = nuw.id
                        usedAmount = nuw.usedAmount + 1
                }
        }

        companion object {
                val idList = mutableListOf<Long>()
                fun getId(): Long {
                        var id: Long = 1
                        while(id < 2 || idList.contains(id)) {
                                id = (1..99999999).random().toLong()
                        }
                        return id
                }
        }
}