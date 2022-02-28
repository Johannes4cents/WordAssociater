package com.example.wordassociater.fire_classes

import com.example.wordassociater.R
import com.google.firebase.firestore.Exclude

data class StoryLine(
        var id: Long = 0,
        var name: String = ""
) {
    var wordsList: MutableList<Word> = mutableListOf()
    var snippetList: MutableList<Snippet> = mutableListOf()
    var eventList: MutableList<Event> = mutableListOf()
    var icon: Icon = Icon.Planet
    var description: String = ""

    enum class Icon { Knife, Planet, Bones, Heart, Fire, Eye, Friends, Letter, Money, Hospital, Science, Computer}

    @Exclude
    var isHeader = false

    @Exclude
    fun getIcon(): Int {
        return when(icon) {
            Icon.Knife -> R.drawable.storyline_knife
            Icon.Planet -> R.drawable.storyline_planet
            Icon.Bones -> R.drawable.storyline_bones
            Icon.Heart -> R.drawable.storyline_heart
            Icon.Fire -> R.drawable.storyline_fire
            Icon.Eye -> R.drawable.storyline_eye
            Icon.Friends -> R.drawable.storyline_friends
            Icon.Letter -> R.drawable.storyline_letter
            Icon.Money -> R.drawable.storyline_money
            Icon.Hospital -> R.drawable.storyline_hospital
            Icon.Science -> R.drawable.storyline_science
            Icon.Computer -> R.drawable.storyline_computer
        }
    }

    @Exclude
    var selected = false

}
