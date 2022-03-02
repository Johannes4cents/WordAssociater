package com.example.wordassociater.fams

import android.view.LayoutInflater
import android.view.View
import com.example.wordassociater.databinding.PopWordClassesBinding
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.utils.Helper

fun popWordClasses(from: View, fam: Fam, onClassPicked: (type: Fam.Class) -> Unit) {
    val b = PopWordClassesBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from)

    b.adjective.setOnClickListener {
        onClassPicked(Fam.Class.Adjective)
        pop.dismiss()
    }

    b.noun.setOnClickListener {
        onClassPicked(Fam.Class.Noun)
        pop.dismiss()
    }

    b.verb.setOnClickListener {
        onClassPicked(Fam.Class.Verb)
        pop.dismiss()
    }
}