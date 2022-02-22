package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarContentBinding

class ContentBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarContentBinding.inflate(LayoutInflater.from(context), this, true)
    lateinit var navController: NavController
    init {
        setClickListener()
    }

    private fun setClickListener() {
        b.btnStrains.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_readFragment)
        }

        b.btnStory.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_storyFragment)
        }

        b.btnNotes.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_notesFragment)
        }

        b.btnDialogue.setOnClickListener {

        }

        b.btnSnippets.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_snippetFragment)
        }
    }
}