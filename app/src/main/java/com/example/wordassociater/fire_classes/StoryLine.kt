package com.example.wordassociater.fire_classes

import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.firestore.FireEvents
import com.example.wordassociater.firestore.FireStoryLines
import com.example.wordassociater.utils.LiveClass
import com.google.firebase.firestore.Exclude

data class StoryLine(
        override var id: Long = 0,
        override var name: String = "",
        var type: Type = Type.Story
): LiveClass {
    var wordList: MutableList<Long> = mutableListOf()
    var wordsList: MutableList<Long> = mutableListOf()
    var snippetsList: MutableList<Long> = mutableListOf()
    var eventList: MutableList<Long> = mutableListOf()
    var icon: Icon = Icon.Planet
    var description: String = ""

    enum class Icon { Knife, Planet, Bones, Heart, Fire, Eye, Friends, Letter, Money, Hospital, Science, Computer}
    enum class Type {Story, SnippetPart}

    @Exclude
    var oldStoryLine: StoryLine? = null

    @Exclude
    val liveWords = MutableLiveData<List<LiveClass>>()

    @Exclude
    val liveSnippets = MutableLiveData<List<LiveClass>>()

    @Exclude
    val liveEvents = MutableLiveData<List<LiveClass>>()

    @Exclude
    override var isAHeader = false
    @Exclude
    override var sortingOrder: Int = id.toInt()

    @Exclude
    override var selected = false
    @Exclude
    override var image: Long = 0

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

    companion object {
        fun getIdList(list: List<StoryLine>): List<Long> {
            val idList = mutableListOf<Long>()
            for(sl in list) {
                idList.add(sl.id)
            }
            return idList
        }
    }

    @Exclude
    fun getWords(): MutableList<Word> {
        val words = mutableListOf<Word>()
        for(id in wordList) {
            val word = Main.getWord(id)
            if(word != null) words.add(word)
        }
        return words
    }

    @Exclude
    fun getFullWordsList() : List<Word> {
        val allWords = Main.wordsList.value!!.toMutableList()
        for(w in allWords) {
            w.selected = getWords().contains(w)
        }
        liveWords.value = allWords
        return allWords
    }

    @Exclude
    fun takeWord(word:Word) {
        if(getWords().contains(word)) {
            wordList.remove(word.id)
        }
        else wordList.add(word.id)
        getFullWordsList()
    }

    @Exclude
    fun updateWords() {
        if(oldStoryLine?.wordList != wordList) {
            // update newly added words snippetLists
            for(id in wordList) {
                if(!oldStoryLine?.wordList?.contains(id)!!) {
                    val word = Main.getWord(id)
                    word!!.storyLineList.add(this.id)
                    FireEvents.update(word.id, "storyLineList", word.storyLineList)
                }
            }
            // update removed words snippetLists
            for(id in oldStoryLine?.wordList!!) {
                if(!wordList.contains(id)) {
                    val word = Main.getWord(id)
                    word!!.storyLineList.remove(this.id)
                    FireEvents.update(word.id, "storyLineList", word.storyLineList)
                    }
                }
            }
            FireStoryLines.update(id, "wordList", wordList)
        }

    @Exclude
    fun copyMe(): StoryLine {
        val copy = StoryLine()
        copy.id = 999999999L
        copy.type = type
        copy.name = name
        copy.wordList = wordList.toMutableList()
        copy.eventList = eventList.toMutableList()
        copy.description = description
        copy.icon = icon
        copy.image = image
        copy.snippetsList = snippetsList.toMutableList()
        oldStoryLine = copy
        return copy
    }

}
