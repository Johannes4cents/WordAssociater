package com.example.wordassociater.lists

import com.example.wordassociater.fire_classes.Note


object NoteLists {
    var storyNotes = mutableListOf<Note>()
    var gameAppNotes = mutableListOf<Note>()
    var wordsAppNotes = mutableListOf<Note>()
    var otherNotes = mutableListOf<Note>()

    fun getList(type: Note.Type): MutableList<Note> {
        return when(type) {
            Note.Type.Story -> storyNotes
            Note.Type.WordsApp -> wordsAppNotes
            Note.Type.GameApp -> gameAppNotes
            Note.Type.Other -> otherNotes
        }
    }
}