package com.example.wordassociater.fire_classes

import com.example.wordassociater.utils.StoryPart

data class Nuw(
        var id: Long = 0,
        var text: String = "",
        var usedIn : MutableList<StoryPart> = mutableListOf(),
        var usedAmount: Int = 0
        ) {

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