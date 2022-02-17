package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.BarSnippetsStrainsBinding
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.firestore.FireSnippets
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.start_fragment.WordLinear
import com.example.wordassociater.story.Story
import com.example.wordassociater.utils.Helper

class SnippetsStrainsBar(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {
    val b = BarSnippetsStrainsBinding.inflate(LayoutInflater.from(context), this, true)
    private val snippetInputOpen = MutableLiveData(false)
    lateinit var navController: NavController

    init {
        setClickListener()
        setObserver()
    }

    private fun setClickListener() {
        b.readBtn.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_readFragment)
        }

        b.writeBtn.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_writeFragment)
        }

        b.snippetInput.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                saveSnippet()
                snippetInputOpen.value = false
                Helper.getIMM(context).hideSoftInputFromWindow(b.snippetInput.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })
    }

    private fun setObserver() {
        snippetInputOpen.observe(context as LifecycleOwner) {
            if(it == true) {
                b.cancelSaveLinear.visibility = View.VISIBLE
                b.leftSnippetButton.setImageResource(R.drawable.btn_snippet_back)
                b.rightSnippetButton.setImageResource(R.drawable.btn_snippet_save)

                b.leftSnippetButton.setOnClickListener {
                    snippetInputOpen.value = false
                }

                b.rightSnippetButton.setOnClickListener {
                    saveSnippet()
                    snippetInputOpen.value = false
                }
            }
            else {
                b.leftSnippetButton.setImageResource(R.drawable.btn_snippets)
                b.rightSnippetButton.setImageResource(R.drawable.btn_new_snippet)


                b.leftSnippetButton.setOnClickListener {
                    navController.navigate(R.id.action_startFragment_to_snippetFragment)
                }

                b.rightSnippetButton.setOnClickListener {
                    snippetInputOpen.value = true
                }
            }
        }

        snippetInputOpen.observe(context as LifecycleOwner) {
            if(it == true) {
                b.snippetInput.visibility = View.VISIBLE
                b.snippetInput.isFocusable = true
                b.snippetInput.isFocusableInTouchMode = true
                b.snippetInput.requestFocus()
                Helper.getIMM(context).showSoftInput(b.snippetInput, 1)
            }
            else {
                closeSnippetInput()
            }
        }

    }

    private fun closeSnippetInput() {
        b.snippetInput.setText("")
        b.snippetInput.visibility = View.GONE
        Helper.getIMM(context).hideSoftInputFromWindow(b.snippetInput.windowToken, 0)
    }

    private fun saveSnippet() {
        if(b.snippetInput.text.isNotEmpty()) {
            val newSnippet = Snippet(
                content = b.snippetInput.text.toString(),
                id = FireStats.getSnippetNumber()
            )
            for(word in WordLinear.selectedWords) {
                newSnippet.wordsUsed.add(word)
            }
            handleCharacters(newSnippet)
            if(Story.storyModeActive) newSnippet.isStory = true
            FireSnippets.add(newSnippet, context)
            closeSnippetInput()
        }
        else {
            closeSnippetInput()
        }
        Helper.deselectWords()
    }

    private fun handleCharacters(snippet: Snippet) {
        for(char in CharacterAdapter.selectedCharacterList) {
            snippet.characterList.add(char)
        }
        CharacterAdapter.selectedCharacterList.clear()
        CharacterAdapter.selectedNameChars.clear()
        CharacterAdapter.characterListTrigger.value = Unit
    }


}