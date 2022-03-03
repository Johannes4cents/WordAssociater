package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.display_filter.FilterSettings

object FireFilter {
    fun add(filter: FilterSettings, context: Context? = null) {
        FireLists.filterList.document("settings").set(filter).addOnSuccessListener {
        }
    }
    fun update(fieldName: String, value: Any) {
        FireLists.filterList.document("settings").update(fieldName, value)
    }

}