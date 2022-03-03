package com.example.wordassociater.fire_classes

data class Item(val id: Long = 0, val name: String = "") {
    val snippetList: MutableList<Snippet> = mutableListOf()
}