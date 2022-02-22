package com.example.wordassociater.fire_classes

data class Note(
    var id: Long = 0,
    var content: String = "",
    var archived: Boolean = false,
    var type: Type = Type.Other) {
    enum class Type {Story, WordsApp, GameApp, Other}
}