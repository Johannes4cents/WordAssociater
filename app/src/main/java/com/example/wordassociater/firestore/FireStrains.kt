package com.example.wordassociater.firestore

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.wordassociater.fire_classes.Strain

object FireStrains {
    fun add(strain: Strain, context: Context) {
        FireLists.fireStrainsList.document(strain.id).set(strain).addOnSuccessListener {
            Toast.makeText(context, "Strain saved to Firestore", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "unable to upload strain to Firestore", Toast.LENGTH_SHORT).show()
        }
    }

    fun update(strain: Strain, context: Context?) {
        Log.i("connectStrains", "$strain")
        FireLists.fireStrainsList.document(strain.id).set(strain)
    }

    fun delete(strain: Strain) {
        FireLists.fireStrainsList.document(strain.id).delete()
    }
}