package com.example.wordassociater.character

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderCharacterBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.snippets.ConnectSnippetsFragment

class CharacterHolder(val b: HolderCharacterBinding): RecyclerView.ViewHolder(b.root) {
    lateinit var character: Character
    lateinit var adapter: CharacterAdapter
    lateinit var takeCharacterFunc: (char: Character) -> Unit
    val charTrigger = MutableLiveData<Unit>()
    fun onBind(character: Character, adapter: CharacterAdapter, takeCharacterFunc:( (char: Character) -> Unit)? = null) {
        this.adapter = adapter
        this.character = character
        if(takeCharacterFunc != null) this.takeCharacterFunc = takeCharacterFunc
        setContent()
        setClickListener()
        setObserver()
    }

    private fun setContent() {
        b.characterName.text = character.name
        if(character.imgUrl != "") Glide.with(b.characterPortrait).load(character.imgUrl).into(b.characterPortrait)

        handleEditMode()
        setClickListener()
    }

    private fun setObserver() {
        CharacterAdapter.characterListTrigger.observe(b.root.context as LifecycleOwner) {
            setContent()
        }

        charTrigger.observe(b.root.context as LifecycleOwner) {
            setContent()
        }

    }

    private fun setClickListener() {
        when(adapter.mode) {
            CharacterAdapter.Mode.LIST -> {
                b.root.setOnClickListener {
                    takeCharacterFunc(character)
                }
            }

            CharacterAdapter.Mode.CONNECTSNIPPETS -> {
                b.root.setOnClickListener {
                    val mutableChars = adapter.characterList?.toMutableList()
                    if (mutableChars?.contains(character)!!) {
                        mutableChars.remove(character)
                        Toast.makeText(b.root.context, "already contains character", Toast.LENGTH_SHORT).show()
                    } else {
                        mutableChars.add(character)
                        Toast.makeText(b.root.context, "character Added", Toast.LENGTH_SHORT).show()
                    }
                    ConnectSnippetsFragment.adapter.submitList(mutableChars)
                    ConnectSnippetsFragment.adapter.notifyDataSetChanged()
                }
            }
            CharacterAdapter.Mode.MAIN -> {
                b.root.setOnClickListener {
                    character.selected = !character.selected
                    takeCharacterFunc(character)
                    charTrigger.value = Unit
                }
            }
            else ->  {}
        }
    }

    private fun removeSelectedFromCharacterWord() {
        var character = Main.characterList.value?.find { char ->
            char.name.equals(character.name, ignoreCase = true)
        }
        if(character != null) {
            CharacterAdapter.selectedCharacterList.remove(character)
            CharacterAdapter.selectedNameChars.remove(character)
            CharacterAdapter.characterListTrigger.value = Unit
        }
    }

    private fun handleEditMode() {
        when(adapter.mode) {
            CharacterAdapter.Mode.LIST -> {
                b.buttonSelected.setImageResource(R.drawable.arrow_right)
            }
            CharacterAdapter.Mode.PREVIEW, CharacterAdapter.Mode.UPDATE, CharacterAdapter.Mode.CONNECTSNIPPETS  -> {
                b.buttonSelected.visibility = View.GONE
                b.characterPortrait.layoutParams.height = 80
                b.characterPortrait.layoutParams.width = 80
                b.characterPortrait.requestLayout()
            }
        }
    }

}