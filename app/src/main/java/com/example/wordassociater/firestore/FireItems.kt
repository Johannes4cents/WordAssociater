package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.fire_classes.Item
import com.example.wordassociater.utils.Helper

object FireItems {
    fun add(item: Item, context: Context? = null) {
        FireLists.itemsList.document(item.id.toString()).set(item).addOnSuccessListener {
            if(context != null) {
                Helper.toast("Item: ${item.name} added", context)
            }
        }
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.itemsList.document(id.toString()).update(fieldName, value)
    }

    fun delete(id: Long) {
        FireLists.itemsList.document(id.toString()).delete()
    }
}