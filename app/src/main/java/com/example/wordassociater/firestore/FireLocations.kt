package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.fire_classes.Location
import com.example.wordassociater.utils.Helper

object FireLocations {
    fun add(location: Location, context: Context? = null) {
        FireLists.locationsList.document(location.id.toString()).set(location).addOnSuccessListener {
            if(context != null) {
                Helper.toast("Location: ${location.name} added", context)
            }
        }
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.locationsList.document(id.toString()).update(fieldName, value)
    }

    fun delete(id: Long) {
        FireLists.locationsList.document(id.toString()).delete()
    }
}