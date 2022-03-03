package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.PopupNewWordcatBinding
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.utils.Helper

fun popNewWordcat(from: View) {
    val b = PopupNewWordcatBinding.inflate(LayoutInflater.from(from.context), null , false)
    val selectedColor = MutableLiveData<View>()
    val pop = Helper.getPopUp(b.root, from, 850, 450)

    val wordCat = WordCat(id = FireStats.getWordCatId())

    b.blue.setOnClickListener {
        wordCat.color = WordCat.Color.Blue
        selectedColor.value = it
    }
    b.green .setOnClickListener{
        wordCat.color = WordCat.Color.Green
        selectedColor.value = it
    }
    b.brown .setOnClickListener{
        wordCat.color = WordCat.Color.Brown
        selectedColor.value = it
    }
    b.pink .setOnClickListener{
        wordCat.color = WordCat.Color.Pink
        selectedColor.value = it

    }
    b.grey .setOnClickListener{
        wordCat.color =  WordCat.Color.Grey
        selectedColor.value = it

    }
    b.purple .setOnClickListener{
        wordCat.color = WordCat.Color.Purple
        selectedColor.value = it
    }
    b.red .setOnClickListener{
        wordCat.color = WordCat.Color.Red
        selectedColor.value = it

    }
    b.blue .setOnClickListener{
        wordCat.color = WordCat.Color.Blue
        selectedColor.value = it

    }
    b.teal.setOnClickListener{
        wordCat.color = WordCat.Color.Teal
        selectedColor.value = it

    }

    b.orange.setOnClickListener {
        wordCat.color = WordCat.Color.Orange
        selectedColor.value = it
    }

    b.black.setOnClickListener{
        wordCat.color = WordCat.Color.Black
        selectedColor.value = it

    }
    b.darkGreen.setOnClickListener{
        wordCat.color = WordCat.Color.DarkGreen
        selectedColor.value = it
    }

    b.darkBlue.setOnClickListener {
        wordCat.color = WordCat.Color.DarkBlue
        selectedColor.value = it
    }
}