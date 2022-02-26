package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.BarNewSnippetBinding
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.WordConnection
import com.example.wordassociater.firestore.FireChars
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.popups.popDramaTypeSelection
import com.example.wordassociater.utils.Drama
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.StoryPart
import com.example.wordassociater.words.WordLinear

class NewSnippetBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarNewSnippetBinding.inflate(LayoutInflater.from(context), this, true)
    val isStory = MutableLiveData(false)
    var isDrama = MutableLiveData<Drama>(Drama.None)

    lateinit var navController: NavController

    init {
        setClickListener()
        setObserver()
    }



    private fun setClickListener() {
        b.btnAddCharacter.setOnClickListener {
            Pop(context).characterRecycler(it, CharacterAdapter.Mode.SELECT)
        }

        b.btnDrama.setOnClickListener {
            popDramaTypeSelection(b.btnDrama, ::handleSelectedDramaType)
        }

        b.btnBack.setOnClickListener {
            closeSnippetInput()
            AddStuffBar.snippetInputOpen.value = false
        }

        b.btnStory.setOnClickListener {

        }

        b.snippetInput.setOnEnterFunc {
            saveSnippet()
            AddStuffBar.snippetInputOpen.value = false
            Helper.getIMM(context).hideSoftInputFromWindow(b.snippetInput.windowToken, 0)
        }
    }

    private fun handleSelectedDramaType(dramaType: Drama) {
        isDrama.value = dramaType
    }

    private fun setObserver() {
        CharacterAdapter.characterListTrigger.observe(context as LifecycleOwner) {
            if(CharacterAdapter.selectedCharacterList.isEmpty()) {
                b.btnAddCharacter.setImageResource(R.drawable.icon_character)
            }
            else {
                b.btnAddCharacter.setImageResource(R.drawable.icon_character_selected)
            }
        }

        AddStuffBar.snippetInputOpen.observe(context as LifecycleOwner) {
            if(it == true) {
                b.snippetInput.visibility = View.VISIBLE
                b.snippetInput.showInputField()
            }
            else {
                closeSnippetInput()
            }
        }

        isStory.observe(context as LifecycleOwner) {
            b.btnStory.setImageResource(if(it) R.drawable.icon_story_selected else R.drawable.icon_story)
        }

        isDrama.observe(context as LifecycleOwner) {
            b.btnDrama.setImageResource(when(it) {
                Drama.Conflict -> R.drawable.icon_conflict
                Drama.Twist -> R.drawable.icon_twist
                Drama.Plan -> R.drawable.icon_plan
                Drama.Motivation -> R.drawable.icon_motivation
                Drama.Goal -> R.drawable.icon_goal
                Drama.Problem -> R.drawable.icon_problem
                Drama.Solution -> R.drawable.icon_solution
                Drama.Hurdle -> R.drawable.icon_hurdle
                Drama.None -> R.drawable.icon_dramaturgy
                Drama.Comedy -> R.drawable.icon_comedy
            })
        }
    }

    private fun closeSnippetInput() {
        b.snippetInput.resetField()
        isDrama.value = Drama.None
        Helper.getIMM(context).hideSoftInputFromWindow(b.snippetInput.windowToken, 0)
    }

    private fun saveSnippet() {
        if(b.snippetInput.content.isNotEmpty()) {
            val newSnippet = Snippet(
                    content = b.snippetInput.content,
                    id = FireStats.getStoryPartId(),
                    drama = isDrama.value!!
            )
            for(word in WordLinear.selectedWords) {
                newSnippet.wordList.add(word.id)
                word.snippetsList.add(newSnippet.id)
                FireWords.update(word.id, "snippetsList", word.snippetsList)
                word.increaseWordUsed()
            }

            WordConnection.connect(newSnippet)
            handleCharacters(newSnippet)
            FireSnippets.add(newSnippet, context)
            closeSnippetInput()
        }
        else {
            closeSnippetInput()
        }
        WordLinear.deselectWords()
    }

    private fun handleCharacters(storyPart: StoryPart) {
        for(char in CharacterAdapter.selectedCharacterList) {
            storyPart.characterList.add(char.id)
            char.snippetsList.add(storyPart.id)
            FireChars.update(char.id, "snippetsList", char.snippetsList)
        }
        CharacterAdapter.selectedCharacterList.clear()
        CharacterAdapter.selectedNameChars.clear()
        CharacterAdapter.characterListTrigger.value = Unit
    }
}