package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import com.example.wordassociater.databinding.PopupDramaTypeSelectorBinding
import com.example.wordassociater.dramaturgy.DramaTypeAdapter
import com.example.wordassociater.fire_classes.Drama
import com.example.wordassociater.utils.Helper

fun popDramaTypeSelection(from: View, takeDramaTypeFunc: (dramaType : Drama.Type) -> Unit) {
    val b = PopupDramaTypeSelectorBinding.inflate(LayoutInflater.from(from.context), null, false)
    val popUp = Helper.getPopUp(b.root, from, 500, 800)

    fun dismissAfterTakeFunc(dramaType: Drama.Type) {
        takeDramaTypeFunc(dramaType)
        popUp.dismiss()
    }

    val adapter = DramaTypeAdapter(::dismissAfterTakeFunc)
    b.dramaTypeRecycler.adapter = adapter

    val typeList = listOf(
            Drama.Type.None,
            Drama.Type.Conflict, Drama.Type.Goal, Drama.Type.Plan, Drama.Type.Hurdle,
            Drama.Type.Motivation, Drama.Type.Problem, Drama.Type.Solution, Drama.Type.Twist)

    adapter.submitList(typeList)

}