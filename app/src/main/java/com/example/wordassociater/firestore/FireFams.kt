package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.utils.Helper

object FireFams {
    fun add(fam: Fam, context: Context? = null) {
        FireLists.famList.document(fam.id.toString()).set(fam).addOnSuccessListener {
            if(context != null) {
                Helper.toast("Fam: ${fam.text} added", context)
            }
        }
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.famList.document(id.toString()).update(fieldName, value)
    }

    fun delete(id: Long) {
        FireLists.famList.document(id.toString()).delete()
    }
    
}