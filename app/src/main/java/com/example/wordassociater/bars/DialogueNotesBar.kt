package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.navigation.NavController
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarDialogueNotesBinding

class DialogueNotesBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarDialogueNotesBinding.inflate(LayoutInflater.from(context), this, true)
    lateinit var navController: NavController

    init {
        setClickListener()
    }

    private fun setClickListener() {
        b.notesButton.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_notesFragment)
        }
    }
}