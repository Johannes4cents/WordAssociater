package com.example.wordassociater.fire_classes

data class Prose(
        var id: Long = 0,
        var content: String = "",
        var snippetList: MutableList<Snippet> = mutableListOf()
        ) {
}