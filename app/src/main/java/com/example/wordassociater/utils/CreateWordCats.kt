package com.example.wordassociater.utils

object CreateWordCats {

    var id: Long = 1
    fun go() {
        val wordCatList = mutableListOf<WordCat>(
                WordCat(
                        id = 1,
                        name = "Character",
                        color = WordCat.Color.Blue
                ),
                WordCat(
                        id = 2,
                        name = "Person",
                        color = WordCat.Color.Blue
                ),
                WordCat(
                        id = 3,
                        name = "Adjective",
                        color = WordCat.Color.Blue
                ),
                WordCat(
                        id = 4,
                        name = "Action",
                        color = WordCat.Color.Blue
                ),
                WordCat(
                        id = 5,
                        name = "Object",
                        color = WordCat.Color.Blue
                ),
                WordCat(
                        id = 6,
                        name = "Place",
                        color = WordCat.Color.Blue
                )
        )
    }
}