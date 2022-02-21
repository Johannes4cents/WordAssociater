package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.BarCharacterStoryBinding
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.story.Story

class CharacterStoryBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    lateinit var navController: NavController
    val b = BarCharacterStoryBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setClickListener()
        setObserver()
    }

    private fun setClickListener() {
        b.writeCharacterButton.setOnClickListener {
            Pop(context).characterRecycler(it, CharacterAdapter.Mode.SELECT)
        }

        b.viewStoriesBtn.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_storyFragment)
        }

        b.writeStoryButton.setOnClickListener {
            handleStoryMode()
        }

    }

    private fun handleStoryMode() {
        Story.storyModeActive = !Story.storyModeActive
        b.writeStoryButton.setImageResource(if(Story.storyModeActive) R.drawable.icon_storymode_selected else R.drawable.icon_write_story)
    }

    private fun setObserver() {
        CharacterAdapter.characterListTrigger.observe(context as LifecycleOwner) {
            if(CharacterAdapter.selectedCharacterList.isEmpty()) {
                b.writeCharacterButton.setImageResource(R.drawable.icon_edit_character)
            }
            else {
                b.writeCharacterButton.setImageResource(R.drawable.icon_edit_character_selected)
            }
        }
    }
}