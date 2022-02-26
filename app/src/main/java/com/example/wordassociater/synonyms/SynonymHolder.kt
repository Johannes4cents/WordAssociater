package com.example.wordassociater.synonyms

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderSynonymBinding

class SynonymHolder(
        val b: HolderSynonymBinding,
        val onHeaderClicked: () -> Unit,
        val onSynonymAdded: (synonym: String) -> Unit
        ): RecyclerView.ViewHolder(b.root) {
    fun onBind(synonym: String) {
        Log.i("synonymHeader", "synonym is $synonym")
        b.synonymText.setTextField(synonym)
        b.synonymText.hideOnEnter()
        b.synonymText.setCenterGravity()
        when (synonym) {
            "synonymHeader" -> {
                b.holderLinear.visibility = View.GONE
                b.headerLinear.visibility = View.VISIBLE

                b.headerLinear.setOnClickListener {
                    onHeaderClicked()
                }
            }
            "" -> {
                b.synonymText.setContentFunc(onSynonymAdded)
                b.synonymText.showInputField()
                b.holderLinear.visibility = View.VISIBLE
                b.headerLinear.visibility = View.GONE
            }
            else -> {
                b.holderLinear.visibility = View.VISIBLE
                b.headerLinear.visibility = View.GONE
            }
        }
    }

}