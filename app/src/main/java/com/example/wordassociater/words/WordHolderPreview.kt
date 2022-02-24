package com.example.wordassociater.words

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderWordPreviewBinding
import com.example.wordassociater.fire_classes.Word

class WordHolderPreview(val b : HolderWordPreviewBinding): RecyclerView.ViewHolder(b.root) {
    private lateinit var word : Word


    fun onBind(word: Word) {
        this.word = word
        setContent()
    }

    private fun setContent() {
        b.content.text = word.text
        b.typeInitials.text = setTypeInitials(word.type)
        b.typeInitials.setBackgroundColor(b.root.context.resources.getColor(word.getColor(word.type)))
    }


    private fun setTypeInitials(type: Word.Type): String {
        return when(type) {
            Word.Type.Adjective -> "Adj"
            Word.Type.Person -> "Per"
            Word.Type.Place -> "Pla"
            Word.Type.Action -> "Act"
            Word.Type.Object -> "Obj"
            Word.Type.CHARACTER -> "Cha"
            Word.Type.NONE -> "Non"
        }
    }


}