package com.example.wordassociater.firestore

import com.example.wordassociater.fire_classes.Nuw

object FireNuws {

    fun add(nuw: Nuw) {
        FireLists.nuwList.document(nuw.id.toString()).set(nuw)
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.nuwList.document(id.toString()).update(fieldName, value)
    }

    fun delete(id: Long) {
        FireLists.nuwList.document(id.toString()).delete()
    }
}