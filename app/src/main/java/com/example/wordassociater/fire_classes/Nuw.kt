package com.example.wordassociater.fire_classes

import com.example.wordassociater.utils.StoryPart

data class Nuw(
        var id: Long = 0,
        var text: String = "",
        var usedIn : MutableList<StoryPart> = mutableListOf()
        ) {

        companion object {
                val idList = mutableListOf<Long>()
        }
}