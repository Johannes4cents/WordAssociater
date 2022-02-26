package com.example.wordassociater.fire_classes

import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

data class StoryLine(
        var id: Long = 0,
        var name: String = "")
{
    var storyPartsList: MutableList<StoryPart> = mutableListOf()
    @Exclude
    var selected = false
}