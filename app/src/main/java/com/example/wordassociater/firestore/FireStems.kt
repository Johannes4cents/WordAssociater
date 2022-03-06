package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.fire_classes.Stem
import com.example.wordassociater.utils.Helper

object FireStems {
    fun add(stem: Stem, context: Context? = null) {
        FireLists.stemList.document(stem.id.toString()).set(stem).addOnSuccessListener {
            if(context != null) {
                Helper.toast("Stem: ${stem.name} added", context)
            }
        }
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.stemList.document(id.toString()).update(fieldName, value)
    }

    fun delete(id: Long) {
        FireLists.stemList.document(id.toString()).delete()
    }
}