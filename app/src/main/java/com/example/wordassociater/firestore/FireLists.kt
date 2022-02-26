package com.example.wordassociater.firestore

import com.example.wordassociater.fire_classes.Note
import com.example.wordassociater.fire_classes.Stats
import com.example.wordassociater.utils.Language
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FireLists {
    private val db = Firebase.firestore
    // noteLists
    private val storyNotes by lazy { db.collection("notes").document("noteCollections").collection("story") }
    private val gameAppNotes by lazy { db.collection("notes").document("noteCollections").collection("gameApp") }
    private val wordsAppNotes by lazy { db.collection("notes").document("noteCollections").collection("wordsApp") }
    private val otherNotes by lazy { db.collection("notes").document("noteCollections").collection("other") }

    // Common Words
    private val commonWordsGerman by lazy { db.collection("commonWords").document("commonWordsCollections").collection("german") }
    private val commonWordsEnglish by lazy { db.collection("commonWords").document("commonWordsCollections").collection("english") }

    val fireStats by lazy { db.collection("stats").document("stats") }
    val bubbleList by lazy { db.collection("bubbles") }
    val dialogueList by lazy { db.collection("dialogue") }
    val characterList by lazy { db.collection("character") }
    val snippetsList by lazy { db.collection("snippets") }
    val spheresList by lazy { db.collection("spheres") }
    val wordCatList by lazy { db.collection("wordCats") }
    val nuwList by lazy {db.collection("nuws") }
    val proseList by lazy { db.collection("prose") }
    val wordConnectionList by lazy { db.collection("wordConnections") }
    val wordsList by lazy { db.collection("words") }

    var stats: Stats? = Stats()


    fun getNoteCollectionRef(type: Note.Type): CollectionReference {
        return when(type) {
            Note.Type.Story -> storyNotes
            Note.Type.WordsApp -> wordsAppNotes
            Note.Type.GameApp -> gameAppNotes
            Note.Type.Other -> otherNotes
        }
    }

    fun getCommonWordsCollectionRef(type: Language): CollectionReference {
        return when(type) {
            Language.German -> commonWordsGerman
            Language.English -> commonWordsEnglish
        }
    }


}