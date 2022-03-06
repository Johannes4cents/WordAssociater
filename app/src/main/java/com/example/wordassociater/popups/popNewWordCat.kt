package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.PopupNewWordcatBinding
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireWordCats
import com.example.wordassociater.utils.Helper

fun popNewWordCat(from: View) {
    val b = PopupNewWordcatBinding.inflate(LayoutInflater.from(from.context), null , false)
    val selectedColor = MutableLiveData<View?>(null)
    val buttonList = mutableListOf(b.blue, b.green, b.brown, b.pink, b.darkBlue, b.darkGreen, b.black, b.orange, b.purple, b.red, b.teal, b.grey)
    val pop = Helper.getPopUp(b.root, from)

    selectedColor.observe(from.context as LifecycleOwner) {
        for(btn in buttonList) {
            btn.setBackgroundColor(if(it == btn) btn.context.resources.getColor(R.color.lightYellow) else btn.context.resources.getColor(R.color.snippets))
        }
    }

    val wordCat = WordCat(id = FireStats.getWordCatId())

    b.blue.setOnClickListener {
        b.inputField.hideInput()
        wordCat.color = WordCat.Color.Blue
        selectedColor.value = it
    }
    b.green .setOnClickListener{
        b.inputField.hideInput()
        wordCat.color = WordCat.Color.Green
        selectedColor.value = it
    }
    b.brown .setOnClickListener{
        b.inputField.hideInput()
        wordCat.color = WordCat.Color.Brown
        selectedColor.value = it
    }
    b.pink .setOnClickListener{
        b.inputField.hideInput()
        wordCat.color = WordCat.Color.Pink
        selectedColor.value = it

    }
    b.grey .setOnClickListener{
        b.inputField.hideInput()
        wordCat.color =  WordCat.Color.Grey
        selectedColor.value = it

    }
    b.purple .setOnClickListener{
        b.inputField.hideInput()
        wordCat.color = WordCat.Color.Purple
        selectedColor.value = it
    }
    b.red .setOnClickListener{
        b.inputField.hideInput()
        wordCat.color = WordCat.Color.Red
        selectedColor.value = it

    }
    b.blue .setOnClickListener{
        b.inputField.hideInput()
        wordCat.color = WordCat.Color.Blue
        selectedColor.value = it

    }
    b.teal.setOnClickListener{
        b.inputField.hideInput()
        wordCat.color = WordCat.Color.Teal
        selectedColor.value = it

    }
    b.orange.setOnClickListener {
        b.inputField.hideInput()
        wordCat.color = WordCat.Color.Orange
        selectedColor.value = it
    }
    b.black.setOnClickListener{
        b.inputField.hideInput()
        wordCat.color = WordCat.Color.Black
        selectedColor.value = it

    }
    b.darkGreen.setOnClickListener{
        b.inputField.hideInput()
        wordCat.color = WordCat.Color.DarkGreen
        selectedColor.value = it
    }
    b.darkBlue.setOnClickListener {
        b.inputField.hideInput()
        wordCat.color = WordCat.Color.DarkBlue
        selectedColor.value = it
    }

    b.inputField.setInputHint("New WordCat?")
    b.inputField.setCenterGravity()
    b.inputField.setTextColorToWhite()
    b.inputField.hideOnEnter()
    b.inputField.setHintInTextField("Enter WordCat name")
    b.inputField.setOnEnterFunc {
        val catNames = mutableListOf<String>()
        for(wc in Main.wordCatsList.value!!) {
            catNames.add(wc.name)
        }
        when {
            it.isEmpty() -> Helper.toast("Category has to have a name", from.context)
            catNames.contains(Helper.stripWordLeaveWhiteSpace(it)) -> Helper.toast("Category already exists", from.context)
            else -> {
                wordCat.name = Helper.stripWordLeaveWhiteSpace(it)
            }
        }

    }

    b.btnBack.setOnClickListener {
        pop.dismiss()
    }

    b.btnSave.setOnClickListener {
        when {
            wordCat.name.isEmpty() -> Helper.toast("A cat needs a name", from.context)
            selectedColor.value == null -> {
                b.inputField.hideInput()
                Helper.toast("Pick a color", from.context)
            }
            else -> {
                FireWordCats.add(wordCat, from.context)
                pop.dismiss()
            }
        }
    }
}