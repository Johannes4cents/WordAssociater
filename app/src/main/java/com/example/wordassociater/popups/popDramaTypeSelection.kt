package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import com.example.wordassociater.databinding.PopupDramaTypeSelectorBinding
import com.example.wordassociater.drama.DramaTypeAdapter
import com.example.wordassociater.utils.Drama
import com.example.wordassociater.utils.Helper

fun popDramaTypeSelection(from: View, takeDramaTypeFunc: (dramaType : Drama) -> Unit) {
    val b = PopupDramaTypeSelectorBinding.inflate(LayoutInflater.from(from.context), null, false)
    val popUp = Helper.getPopUp(b.root, from, 500, 800)

    fun dismissAfterTakeFunc(dramaType: Drama) {
        takeDramaTypeFunc(dramaType)
        popUp.dismiss()
    }

    val adapter = DramaTypeAdapter(::dismissAfterTakeFunc)
    b.dramaTypeRecycler.adapter = adapter

    val typeList = listOf(
            Drama.None,
            Drama.Conflict, Drama.Goal, Drama.Plan, Drama.Hurdle,
            Drama.Motivation, Drama.Problem, Drama.Solution, Drama.Twist)

    adapter.submitList(typeList)

}