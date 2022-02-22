package com.example.wordassociater.firestore

import android.content.Context
import com.example.wordassociater.fire_classes.Sphere
import com.example.wordassociater.utils.Helper

object FireSpheres {
    fun add(sphere: Sphere, context: Context?) {
        FireLists.spheresList.document(sphere.id.toString()).set(sphere).addOnSuccessListener {
            if(context != null) Helper.toast("Sphere added", context)
        }.addOnFailureListener {
            if(context != null) Helper.toast("Could not add Sphere", context)
        }
    }
}