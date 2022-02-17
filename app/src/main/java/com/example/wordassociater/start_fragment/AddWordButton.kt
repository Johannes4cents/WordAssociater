package com.example.wordassociater.start_fragment

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.wordassociater.R
import com.example.wordassociater.bars.HandleWordsBar
import com.example.wordassociater.databinding.ButtonAddWordBinding

class AddWordButton(context: Context, attributeSet: AttributeSet?): LinearLayout(context, attributeSet) {
    val b = ButtonAddWordBinding.inflate(LayoutInflater.from(context), this, true)
    val category: Int

    init {
        context.theme.obtainStyledAttributes(
                attributeSet,
                R.styleable.AddWordButton,
                0, 0).apply {

            try {
                category = getInteger(R.styleable.AddWordButton_category, 0)
            } finally {
                recycle()
            }
        }
        b.btnImage.setImageResource(setBackground())
        setClickListenerAndDescription()
    }

    private fun setBackground(): Int {
        return when(category) {
            0 -> R.drawable.btn_add_adjective
            1 -> R.drawable.btn_add_action
            2 -> R.drawable.btn_add_person
            3 -> R.drawable.btn_add_hero
            4 -> R.drawable.btn_add_object
            5 -> R.drawable.btn_add_place
            else -> R.drawable.btn_add_object
        }
    }

    private fun setClickListenerAndDescription() {
        when(category) {
            0 -> {
                b.descriptionText.text = "Adjective"
                b.btnImage.setOnClickListener {
                    WordLinear.wordList.add(HandleWordsBar.getWord(Word.Type.Adjective))
                    WordLinear.wordListTriger.postValue(Unit)
                }
            }
            1 -> {
                b.descriptionText.text = "Action"
                b.btnImage.setOnClickListener {
                    WordLinear.wordList.add(HandleWordsBar.getWord(Word.Type.Action))
                    WordLinear.wordListTriger.postValue(Unit)
                }
            }
            2 -> {
                b.descriptionText.text = "Person"
                b.btnImage.setOnClickListener {
                    WordLinear.wordList.add(HandleWordsBar.getWord(Word.Type.Person))
                    WordLinear.wordListTriger.postValue(Unit)
                }
            }
            3 -> {
                b.descriptionText.text = "Character"
                b.btnImage.setOnClickListener {
                    WordLinear.wordList.add(HandleWordsBar.getWord(Word.Type.CHARACTER))
                    WordLinear.wordListTriger.postValue(Unit)
                }
            }
            4 -> {
                b.descriptionText.text = "Object"
                b.btnImage.setOnClickListener {
                    WordLinear.wordList.add(HandleWordsBar.getWord(Word.Type.Object))
                    WordLinear.wordListTriger.postValue(Unit)
                }
            }
            5 -> {
                b.descriptionText.text = "Place"
                b.btnImage.setOnClickListener {
                    WordLinear.wordList.add(HandleWordsBar.getWord(Word.Type.Place))
                    WordLinear.wordListTriger.postValue(Unit)
                }
            }
        }










    }

}