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
import com.example.wordassociater.fire_classes.Drama
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.WordConnection
import com.example.wordassociater.firestore.*
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.popups.popDramaTypeSelection
import com.example.wordassociater.story.Story
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.StoryPart
import com.example.wordassociater.words.WordLinear

class NewSnippetBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarNewSnippetBinding.inflate(LayoutInflater.from(context), this, true)
    val isStory = MutableLiveData(false)
    var isDrama = MutableLiveData<Drama.Type>(Drama.Type.None)

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
            isStory.value = !isStory.value!!
        }

        b.snippetInput.setOnEnterFunc {
            saveSnippet()
            AddStuffBar.snippetInputOpen.value = false
            Helper.getIMM(context).hideSoftInputFromWindow(b.snippetInput.windowToken, 0)
        }
    }

    private fun handleSelectedDramaType(dramaType: Drama.Type) {
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
                Drama.Type.Conflict -> R.drawable.icon_conflict
                Drama.Type.Twist -> R.drawable.icon_twist
                Drama.Type.Plan -> R.drawable.icon_plan
                Drama.Type.Motivation -> R.drawable.icon_motivation
                Drama.Type.Goal -> R.drawable.icon_goal
                Drama.Type.Problem -> R.drawable.icon_problem
                Drama.Type.Solution -> R.drawable.icon_solution
                Drama.Type.Hurdle -> R.drawable.icon_hurdle
                Drama.Type.None -> R.drawable.icon_dramaturgy
            })
        }
    }

    private fun closeSnippetInput() {
        b.snippetInput.resetField()
        isDrama.value = Drama.Type.None
        Helper.getIMM(context).hideSoftInputFromWindow(b.snippetInput.windowToken, 0)
    }

    private fun saveSnippet() {
        if(isDrama.value != Drama.Type.None) {
            saveAsDrama()
        }
        saveAsSnippet()
    }

    private fun saveAsSnippet() {
        if(b.snippetInput.content.isNotEmpty()) {
            val newSnippet = Snippet(
                    content = b.snippetInput.content,
                    id = FireStats.getStoryPartId(),
                    isStory = isStory.value!!,
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
            if(Story.storyModeActive) newSnippet.isStory = true
            FireSnippets.add(newSnippet, context)
            closeSnippetInput()
        }
        else {
            closeSnippetInput()
        }
        WordLinear.deselectWords()
    }

    private fun saveAsDrama() {
        if(b.snippetInput.content.isNotEmpty()) {
            val newDrama = Drama(
                    content = b.snippetInput.content.toString(),
                    id = FireStats.getDramaId(),
            )
            for(word in WordLinear.selectedWords) {
                newDrama.wordList.add(word.id)
            }
            handleCharacters(newDrama)
            FireDrama.add(newDrama, context)
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