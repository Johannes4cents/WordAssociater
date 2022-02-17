package com.example.wordassociater.firestore

import com.example.wordassociater.fire_classes.Stats
import com.example.wordassociater.fire_classes.Word
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FireLists {
    private val db = Firebase.firestore
    private val actionList by lazy { db.collection("words").document("wordCollections").collection("actions") }
    private val adjectivesList by lazy { db.collection("words").document("wordCollections").collection("adjectives") }
    private val personsList by lazy { db.collection("words").document("wordCollections").collection("persons") }
    private val heroesList by lazy { db.collection("words").document("wordCollections").collection("heroes") }
    private val placesList by lazy { db.collection("words").document("wordCollections").collection("places") }
    private val objectsList by lazy { db.collection("words").document("wordCollections").collection("objects") }
    val fireStats by lazy { db.collection("stats").document("stats") }
    val characterList by lazy { db.collection("character") }
    val fireStrainsList by lazy { db.collection("strains") }
    val snippetsList by lazy { db.collection("snippets") }
    val noteList by lazy { db.collection("notes") }
    var stats: Stats? = Stats()

    fun getCollectionRef(type: Word.Type): CollectionReference {
        return when(type) {
            Word.Type.Adjective -> adjectivesList
            Word.Type.Person -> personsList
            Word.Type.Place -> placesList
            Word.Type.Action -> actionList
            Word.Type.Object -> objectsList
            Word.Type.CHARACTER -> heroesList
            Word.Type.NONE -> objectsList
        }
    }
}