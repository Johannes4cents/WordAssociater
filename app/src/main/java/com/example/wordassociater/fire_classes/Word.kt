package com.example.wordassociater.fire_classes

data class Word(
        val text: String = "",
        val type: Type = Type.NONE,
        var id: String = "",
        val snippetList: MutableList<Long> = mutableListOf(),
        var strainList: MutableList<String> = mutableListOf(),
        var selected : Boolean = false,
        var lineCount : Int = 1,
        var charNumber: Long = 0,
        var imgUrl: String = "")
{
    var isHeader = false
    enum class Type(val text: String) {
        Adjective("Adjective"),
        Person("Person"),
        Place("Place"),
        Action("Action"),
        Object("Object"),
        CHARACTER("Character"),
        NONE("None") }
}