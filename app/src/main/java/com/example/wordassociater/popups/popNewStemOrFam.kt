package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import com.example.wordassociater.Main
import com.example.wordassociater.databinding.PopNewStemOrFamBinding
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.fire_classes.Stem
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.LiveClass
import java.util.*

fun popNewStemOrFam(from: View, onSaved: (item: LiveClass) -> Unit, forStems: Boolean) {
    val b = PopNewStemOrFamBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from)

    fun saveStem(name: String) {
        val existingStem = Stem.checkIfAlreadyExists(name)
        if( existingStem == null) {
            val stem = Stem(id = FireStats.getStemId(), name = Helper.stripWordLeaveWhiteSpace(name))
            onSaved(stem)
        }
        else Helper.toast("That is already a stem of ${Main.getWord(existingStem.word)?.text} | stem is ${existingStem.name} - id: ${existingStem.id}", from.context)
    }

    fun saveFam(name: String) {
        val existingFam = Fam.checkIfAlreadyExists(name)
        if( existingFam == null) {
            val fam = Fam(id = FireStats.getStemId(), text = Helper.stripWordLeaveWhiteSpace(name))
            fam.name = Helper.stripWordLeaveWhiteSpace(name)

            val foundStem = Stem.checkFamIfInStems(fam.text)
            if(foundStem == null) onSaved(fam)
            else Helper.toast("already a stem of ${Main.getWord(foundStem.word)?.name} | stem is ${foundStem.name} | stemId is ${foundStem.id}", from.context)
        }
        else Helper.toast("That is already a fam of ${Main.getWord(existingFam.word)?.name}", from.context)
    }

    fun trySave(it: String) {
        if(it != "" && it != " " && it.length > 3 && it.toLowerCase(Locale.ROOT).capitalize(Locale.ROOT) != "Any") {
            if(forStems) {
                saveStem(it)
            }
            else {
                saveFam(it)
            }
        }
        else Helper.toast("incorrect Input", from.context)
        pop.dismiss()
    }

    b.inputField.apply {
        setCenterGravity()
        setTextColorToWhite()
        setSingleLine()
        setInputHint(if(forStems) "new Stem" else "new Fam")
        showInputField()
    }

    b.btnBack.setOnClickListener { pop.dismiss() }

    b.btnSave.setOnClickListener { trySave(b.inputField.content) }

    b.inputField.setOnEnterFunc {
        trySave(it)
    }

}