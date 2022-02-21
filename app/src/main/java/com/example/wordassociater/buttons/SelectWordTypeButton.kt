package com.example.wordassociater.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.ButtonSelectWordTypeBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.start_fragment.WordLinear
import com.example.wordassociater.words_list_fragment.WordsListFragment

class SelectWordTypeButton(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = ButtonSelectWordTypeBinding.inflate(LayoutInflater.from(context), this, true)
    private val category: Int
    private val type: Word.Type

    companion object {
        val selectedButton = MutableLiveData<SelectWordTypeButton>()
    }

    init {
        context.theme.obtainStyledAttributes(
                attributeSet,
                R.styleable.SelectWordTypeButton,
                0, 0).apply {

            try {
                category = getInteger(R.styleable.SelectWordTypeButton_selectCat, 0)
            } finally {
                recycle()
            }
        }

        setContent()
        setClickListener()
        observeToSubmitList()
        type = getType(category)
    }

    private fun getType(i: Int): Word.Type {
        return when(category) {
            0 -> Word.Type.Adjective
            1 -> Word.Type.Action
            2 -> Word.Type.Person
            3 -> Word.Type.CHARACTER
            4 -> Word.Type.Object
            5 -> Word.Type.Place
            else -> Word.Type.Object
        }
    }

    private fun setContent() {
        b.lineImage.setImageResource(getImageInt(category))
        b.buttonText.text = getCategoryName(category)
    }

    private fun getImageInt(i: Int): Int {
        return when(category) {
            0 -> R.drawable.line_adjective
            1 -> R.drawable.line_action
            2 -> R.drawable.line_person
            3 -> R.drawable.line_character
            4 -> R.drawable.line_object
            5 -> R.drawable.line_place
            else -> R.drawable.line_object
        }
    }

    private fun getCategoryName(i: Int) : String {
        return when(i) {
            0 -> "Adjectives"
            1 -> "Actions"
            2 -> "Persons"
            3 -> "Character"
            4 -> "Object"
            5 -> "Place"
            else -> "None"
        }
    }

    private fun setClickListener() {
        b.root.setOnClickListener { selectedButton.value = this }
    }

    private fun handleSelectedVisual() {
        selectedButton.observe(context as LifecycleOwner) {
            if(it == this) {
                b.lineImage.setImageResource(R.drawable.line_selected)
            }
            else {
                b.lineImage.setImageResource(getImageInt(category))
            }
        }
    }

    private fun observeToSubmitList() {
        selectedButton.observe(context as LifecycleOwner) {
            if(it == this) WordsListFragment.currentList.value = WordLinear.getWordList(type)
        }
    }
}