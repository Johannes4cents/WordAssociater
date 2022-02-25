package com.example.wordassociater.firestore

import com.example.wordassociater.fire_classes.Drama
import com.example.wordassociater.fire_classes.Note
import com.example.wordassociater.fire_classes.Stats
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

    // DramaLists Conflict, Twist, Plan, Motivation, Goal, Problem, Solution, Hurdle, None
    private val conflictList by lazy { db.collection("dramas").document("dramaCollections").collection("conflicts") }
    private val twistList by lazy { db.collection("dramas").document("dramaCollections").collection("twists") }
    private val planList by lazy { db.collection("dramas").document("dramaCollections").collection("plans") }
    private val motivationList by lazy { db.collection("dramas").document("dramaCollections").collection("motivations") }
    private val goalList by lazy { db.collection("dramas").document("dramaCollections").collection("goals") }
    private val problemList by lazy { db.collection("dramas").document("dramaCollections").collection("problems") }
    private val solutionList by lazy { db.collection("dramas").document("dramaCollections").collection("solutions") }
    private val hurdleList by lazy { db.collection("dramas").document("dramaCollections").collection("hurdles") }

    val fireStats by lazy { db.collection("stats").document("stats") }
    val bubbleList by lazy { db.collection("bubbles") }
    val dialogueList by lazy { db.collection("dialogue") }
    val characterList by lazy { db.collection("character") }
    val fireStrainsList by lazy { db.collection("strains") }
    val snippetsList by lazy { db.collection("snippets") }
    val spheresList by lazy { db.collection("spheres") }
    val wordCatList by lazy { db.collection("wordCats") }
    val nuwList by lazy {db.collection("nuws") }
    val proseList by lazy { db.collection("prose") }
    val wordConnectionList by lazy { db.collection("wordConnections") }
    val wordsList by lazy { db.collection("words") }

    var stats: Stats? = Stats()


    fun getDramaCollectionRef(type: Drama.Type): CollectionReference {
        return when(type) {
            Drama.Type.Conflict -> conflictList
            Drama.Type.Twist -> twistList
            Drama.Type.Plan -> planList
            Drama.Type.Motivation -> motivationList
            Drama.Type.Goal -> goalList
            Drama.Type.Problem -> problemList
            Drama.Type.Solution -> solutionList
            Drama.Type.Hurdle -> hurdleList
            Drama.Type.None -> conflictList
        }
    }

    fun getNoteCollectionRef(type: Note.Type): CollectionReference {
        return when(type) {
            Note.Type.Story -> storyNotes
            Note.Type.WordsApp -> wordsAppNotes
            Note.Type.GameApp -> gameAppNotes
            Note.Type.Other -> otherNotes
        }
    }
}