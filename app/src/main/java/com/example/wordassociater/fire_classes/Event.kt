package com.example.wordassociater.fire_classes

import com.example.wordassociater.R
import com.example.wordassociater.utils.Date
import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

class Event(
        override var id: Long = 0,
        override var content: String = "",
        override var characterList: MutableList<Long> = mutableListOf(22),
        override var wordList: MutableList<Long> = mutableListOf(0),
        override var nuwList: MutableList<Long> = mutableListOf(),
        override var storyLineList: MutableList<Long> = mutableListOf(),
        override var date: Date = Date(0,"May",1100),
        override var type: Type = Type.Event,
        var image: Image = Image.Airplane
): StoryPart(id, content, characterList, wordList, nuwList, storyLineList, date, type) {
    enum class Image { Airplane, Crown, Explosion, Food, Handshake, Party, Pistol, Shield }

    @Exclude
    fun getImage(): Int {
        return when(image) {
            Image.Airplane -> R.drawable.event_airplane
            Image.Crown -> R.drawable.event_crown
            Image.Explosion -> R.drawable.event_explosion
            Image.Food -> R.drawable.event_food
            Image.Handshake -> R.drawable.event_handshake
            Image.Party -> R.drawable.event_party
            Image.Pistol -> R.drawable.event_pistol
            Image.Shield -> R.drawable.event_shield
        }
    }
}