package com.example.wordassociater.fire_classes

import com.example.wordassociater.R
import com.google.firebase.firestore.Exclude

class Sphere(
        var id: Long = 0,
        var name: String = "",
        var color: Color = Color.Green
) {
    enum class Color { Red, Green, Gold }

    @Exclude
    fun getColor(color: Color): Int {
        return when(color) {
            Color.Red -> R.drawable.sphere_red
            Color.Green -> R.drawable.sphere_green
            Color.Gold -> R.drawable.sphere_gold
        }
    }
}