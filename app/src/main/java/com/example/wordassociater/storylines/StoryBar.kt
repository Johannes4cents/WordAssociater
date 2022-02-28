package com.example.wordassociater.storylines

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.BarStoryBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Word

class StoryBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarStoryBinding.inflate(LayoutInflater.from(context), this, true)
    val selectedWords = MutableLiveData<List<Word>>(mutableListOf())
    val selectedCharacter = MutableLiveData<List<Character>>(mutableListOf())

    init {
        setClickListener()
    }


    private fun setClickListener() {
        b.btnDialogue.setOnClickListener {

        }
        b.btnBack.setOnClickListener {

        }
        b.btnViewFilter.setOnClickListener {

        }
        b.btnCharacter.setOnClickListener {

        }
        b.btnWord.setOnClickListener {

        }
        b.iconSnippets.setOnClickListener {

        }
    }



}