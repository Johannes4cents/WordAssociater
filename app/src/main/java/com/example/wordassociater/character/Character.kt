package com.example.wordassociater.character

import com.example.wordassociater.utils.Snippet
import com.example.wordassociater.strain_edit_fragment.Strain

data class Character(

        val name: String = "", val imgUrl : String = "",
        val strainsList: MutableList<Strain> = mutableListOf(),
        val snippetsList: MutableList<Snippet> = mutableListOf(),
        var id: String = "",
        var charNumber: Long = 0,
        var importance: Importance = Importance.Side) {
        var selected = false
        var isHeader = false
        enum class Importance(val text: String) {Main("Main"), Side("Side"), Minor("Minor"), Mentioned("Mentioned")}
}

