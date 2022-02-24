package com.example.wordassociater.fire_classes

import com.example.wordassociater.utils.StoryPart

class StoryLine(
        var id: Long = 0,
        var name: String = "",
        var storyPartsList: MutableList<StoryPart> = mutableListOf()
)
{
}