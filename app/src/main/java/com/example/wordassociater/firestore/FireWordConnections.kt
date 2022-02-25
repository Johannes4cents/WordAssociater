package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.fire_classes.WordConnection
import com.example.wordassociater.utils.Helper

object FireWordConnections {

    fun add(wc: WordConnection, context: Context?) {
        FireLists.wordConnectionList.document(wc.id.toString()).set(wc).addOnSuccessListener {
            if(context != null) Helper.toast("WordConnection uploaded", context)
        }
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.wordConnectionList.document(id.toString()).update(fieldName, value)
    }

    fun delete(id: Long) {
        FireLists.wordConnectionList.document(id.toString()).delete()
    }


}