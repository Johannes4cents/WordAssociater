package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.fire_classes.Event
import com.example.wordassociater.utils.Helper

object FireEvents {
    fun add(event: Event, context: Context? = null) {
        FireLists.eventsList.document(event.id.toString()).set(event).addOnSuccessListener {
            if(context != null) {
                Helper.toast("Event: ${event.name} added", context)
            }
        }
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.eventsList.document(id.toString()).update(fieldName, value)
    }

    fun delete(id: Long) {
        FireLists.eventsList.document(id.toString()).delete()
    }
}