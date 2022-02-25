package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import com.example.wordassociater.databinding.PopupColorPickerBinding
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.utils.Helper

fun popColorPicker(from: View, takeColor: (color: WordCat.Color) -> Unit) {
    val b = PopupColorPickerBinding.inflate(LayoutInflater.from(from.context), null , false)
    val pop = Helper.getPopUp(b.root, from, 850, 450)

    b.blue.setOnClickListener {
        takeColor(WordCat.Color.Blue)
        pop.dismiss()
    }
    b.green .setOnClickListener{
        takeColor(WordCat.Color.Green)
        pop.dismiss()
    }
    b.brown .setOnClickListener{
        takeColor(WordCat.Color.Brown)
        pop.dismiss()
    }
    b.pink .setOnClickListener{
        takeColor(WordCat.Color.Pink)
        pop.dismiss()
    }
    b.grey .setOnClickListener{
        takeColor(WordCat.Color.Grey)
        pop.dismiss()
    }
    b.purple .setOnClickListener{
        takeColor(WordCat.Color.Purple)
        pop.dismiss()
    }
    b.red .setOnClickListener{
        takeColor(WordCat.Color.Red)
        pop.dismiss()
    }
    b.blue .setOnClickListener{
        takeColor(WordCat.Color.Blue)
        pop.dismiss()
    }
    b.teal.setOnClickListener{
        takeColor(WordCat.Color.Teal)
        pop.dismiss()
    }

    b.orange.setOnClickListener {
        takeColor(WordCat.Color.Orange)
        pop.dismiss()
    }
}