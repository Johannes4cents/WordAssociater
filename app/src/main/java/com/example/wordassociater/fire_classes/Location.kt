package com.example.wordassociater.fire_classes

import com.example.wordassociater.R
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireWords
import com.google.firebase.firestore.Exclude

data class Location(
        var id: Long = 0,
        var name: String = "",
        var connectId: Long = 0
        ) {
    var imgUrl: String = ""
    var image:Image = Image.Island
    var snippetList: MutableList<Long> = mutableListOf()
    var wordList: MutableList<Long> = mutableListOf()

    enum class Image { House, Car, Forrest, City, Lab, Island, Street, Factory, Apartment}

    @Exclude
    fun createWord() {
        val connectId = FireStats.getConnectId()
        val word = Word(
                id = FireStats.getWordId(),
                text = name,
                connectId = connectId
        )
        word.cats.add(7)
        this.connectId = connectId
        wordList.add(word.id)
        FireWords.add(word)
    }

    @Exclude
    fun getImage(): Int {
        return when(image) {
            Image.House -> R.drawable.location_house
            Image.Car -> R.drawable.location_car
            Image.Forrest -> R.drawable.location_forrest
            Image.City -> R.drawable.location_city
            Image.Lab -> R.drawable.location_lab
            Image.Island -> R.drawable.location_island
            Image.Street -> R.drawable.location_street
            Image.Factory -> R.drawable.location_factory
            Image.Apartment -> R.drawable.location_apartment
        }
    }
}