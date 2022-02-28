package com.example.wordassociater.firestore

import com.example.wordassociater.fire_classes.Event

object FireEvents {
    fun add(event: Event) {
        FireLists.eventsList.document(event.id.toString()).set(event)
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.eventsList.document(id.toString()).update(fieldName, value)
    }

    fun delete(id: Long) {
        FireLists.eventsList.document(id.toString()).delete()
    }
}