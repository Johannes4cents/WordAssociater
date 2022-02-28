package com.example.wordassociater.firestore

import com.example.wordassociater.fire_classes.Prose

object FireProse {
    fun add(prose: Prose) {
        FireLists.proseList.document(prose.id.toString()).set(prose)
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.proseList.document(id.toString()).update(fieldName, value)
    }

    fun delete(id: Long) {
        FireLists.proseList.document(id.toString()).delete()
    }
}