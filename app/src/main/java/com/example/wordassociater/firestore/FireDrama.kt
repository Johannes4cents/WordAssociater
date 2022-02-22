package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.fire_classes.Drama
import com.example.wordassociater.utils.Helper

object FireDrama {
    val typeList = listOf(
            Drama.Type.Conflict, Drama.Type.Goal, Drama.Type.Plan, Drama.Type.Hurdle,
            Drama.Type.Motivation, Drama.Type.Problem, Drama.Type.Solution, Drama.Type.Twist)

    fun add(drama: Drama, context: Context?) {
        FireLists.getDramaCollectionRef(drama.type).document(drama.id.toString()).set(drama).addOnSuccessListener {
            if(context != null) Helper.toast("Dramaturgy added", context)
        }.addOnFailureListener {
            if(context != null) Helper.toast("could not add dramaturgy", context)
        }
    }
}