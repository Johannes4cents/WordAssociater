package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.utils.Helper

object FireWordCats {
    fun add(wordCat: WordCat, context: Context?) {
        FireLists.wordCatList.document(wordCat.id.toString()).set(wordCat).addOnSuccessListener {
            if(context != null) Helper.toast("WordCat Added", context)
        }.addOnFailureListener {
            if(context != null) Helper.toast("couldn't add wordCat", context)
        }
    }

    fun update(id: Long, fieldName: String, value: Any){
        FireLists.wordCatList.document(id.toString()).update(fieldName, value)
    }
}