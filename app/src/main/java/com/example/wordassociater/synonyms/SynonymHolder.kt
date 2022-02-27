package com.example.wordassociater.synonyms

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderSynonymBinding

class SynonymHolder(
        val b: HolderSynonymBinding,
        val onHeaderClicked: () -> Unit,
        private val onSynonymAdded: (synonym: String) -> Unit
        ): RecyclerView.ViewHolder(b.root) {
    lateinit var synonym : String
    fun onBind(type: SynonymRecycler.Type, synonym: String) {
        this.synonym = synonym
        when(type) {
            SynonymRecycler.Type.List -> setForList()
            SynonymRecycler.Type.Popup -> setForPopUp()
        }
    }

    private fun setForList() {
        b.synonymText.disableNuwInput()
        b.synonymText.enableTwiceClickSafety()
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
                b.synonymText.enableOutSideEditClickCheck(false)
            }
            else -> {
                b.holderLinear.visibility = View.VISIBLE
                b.headerLinear.visibility = View.GONE
            }
        }
    }

    private fun setForPopUp() {
        b.holderLinear.visibility = View.GONE
        b.pickerLinear.visibility = View.VISIBLE
        b.root.isClickable = true
        b.synonymTextPicker.text = synonym
        b.synonymTextPicker.setOnClickListener {
            onSynonymAdded(synonym)
        }


    }
}