package com.example.wordassociater.stems

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderStemBinding
import com.example.wordassociater.words.HeritageFragment

class StemsHolder(
        val b: HolderStemBinding,
        val onHeaderClicked: () -> Unit,
        private val onStemAdded: (stem: String) -> Unit
        ): RecyclerView.ViewHolder(b.root) {
    fun onBind(stem: String) {
        b.stemText.disableNuwInput()
        b.stemText.enableTwiceClickSafety()
        b.stemText.setTextField(stem)
        b.stemText.hideOnEnter()
        b.stemText.setCenterGravity()
        Log.i("saveSte", "StemsHolder word.synonyms are : ${HeritageFragment.word.synonyms}")
        when (stem) {

            "stemHeader" -> {
                b.holderLinear.visibility = View.GONE
                b.headerLinear.visibility = View.VISIBLE

                b.headerLinear.setOnClickListener {
                    onHeaderClicked()
                }
            }
            "" -> {
                b.stemText.setContentFunc(onStemAdded)
                b.stemText.showInputField()
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