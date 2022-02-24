package com.example.wordassociater.firestore

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.wordassociater.fire_classes.Strain

object FireStrains {
    fun add(strain: Strain, context: Context?) {
        Log.i("characterProb", "in FireStrains/add - characterList is ${strain.characterList}")
        FireLists.fireStrainsList.document(strain.id.toString()).set(strain).addOnSuccessListener {
            Log.i("characterProb", "Strain saved to Firestore")
            if(context != null) Toast.makeText(context, "Strain saved to Firestore", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Log.i("characterProb", "strain failed to upload")
            if(context != null) Toast.makeText(context, "unable to upload strain to Firestore", Toast.LENGTH_SHORT).show()
        }.addOnCanceledListener {
            Log.i("characterProb", "upload canceled")
        }
    }

    fun update(id: Long, fieldName: String, value: Any) {
        FireLists.fireStrainsList.document(id.toString()).update(fieldName, value)
    }

    fun delete(id: Long) {
        FireLists.fireStrainsList.document(id.toString()).delete()
    }
}