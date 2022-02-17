package com.example.wordassociater.character

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.connect_snippets_fragment.ConnectSnippetsFragment
import com.example.wordassociater.databinding.HolderCharacterBinding
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.strain_list_fragment.StrainListFragment
import com.example.wordassociater.strain_edit_fragment.StrainEditFragment

class CharacterHolder(val b: HolderCharacterBinding): RecyclerView.ViewHolder(b.root) {
    lateinit var character: Character
    lateinit var adapter: CharacterAdapter
    lateinit var func: (char: Character) -> Unit
    fun onBind(character: Character , adapter: CharacterAdapter, func:( (char: Character) -> Unit)? = null) {
        this.adapter = adapter
        this.character = character
        if(func != null) this.func = func
        setContent()
        setClickListener()
        setObserver()
    }

    private fun setContent() {
        character.selected = CharacterAdapter.selectedCharacterList.contains(character)
        b.characterName.text = character.name
        if(character.imgUrl != "") Glide.with(b.characterPortrait).load(character.imgUrl).into(b.characterPortrait)

        handleEditMode()
        setClickListener()
    }

    private fun setObserver() {
        CharacterAdapter.characterListTrigger.observe(b.root.context as LifecycleOwner) {
            setContent()
        }

    }

    private fun setClickListener() {
        when(adapter.mode) {
            CharacterAdapter.Mode.LIST -> {
                b.root.setOnClickListener {
                    CharacterListFragment.selectedCharacter.value = character
                }
            }
            CharacterAdapter.Mode.SELECT -> {
                b.root.setOnClickListener {
                    character.selected = !character.selected
                    if(character.selected) {
                        CharacterAdapter.selectedCharacterList.add(character)
                    }
                    else {
                        CharacterAdapter.selectedCharacterList.remove(character)
                        removeSelectedFromCharacterWord()

                    }
                    CharacterAdapter.characterListTrigger.value = Unit
                }
            }
            CharacterAdapter.Mode.PREVIEW -> {

            }
            CharacterAdapter.Mode.UPDATE -> {
                b.root.setOnClickListener {

                    // add Character to the preview Adapter in the WriteFragment
                    val openStrainCharList = StrainListFragment.openStrain.value?.characterList
                    if(openStrainCharList?.contains(character)!!) {
                        StrainListFragment.openStrain.value?.characterList?.remove(character)
                    }
                    else {
                        StrainListFragment.openStrain.value?.characterList?.add(character)
                    }
                    StrainEditFragment.adapter.submitList(StrainListFragment.openStrain.value?.characterList)
                    StrainEditFragment.adapter.notifyDataSetChanged()

                    // Marks Character as selected in the Update Popup character List
                    val index = Pop.characterListUpdate.indexOf(character)
                    Pop.characterListUpdate[index].selected = !Pop.characterListUpdate[index].selected
                    Pop.characterAdapter.submitList(Pop.characterListUpdate)
                }
            }
            CharacterAdapter.Mode.CONNECTSNIPPETS -> {
                b.root.setOnClickListener {
                    if (adapter.characterList?.contains(character)!!) {
                        adapter.characterList!!.remove(character)
                        Toast.makeText(b.root.context, "already contains character", Toast.LENGTH_SHORT).show()
                    } else {
                        adapter.characterList!!.add(character)
                        Toast.makeText(b.root.context, "character Added", Toast.LENGTH_SHORT).show()
                    }
                    ConnectSnippetsFragment.adapter.submitList(adapter.characterList)
                    ConnectSnippetsFragment.adapter.notifyDataSetChanged()
                }
            }
            CharacterAdapter.Mode.EDITSNIPPETS -> {
                b.root.setOnClickListener {
                    func(character)
                }
            }
        }
    }

    private fun removeSelectedFromCharacterWord() {
        var character = Main.characterList.find { char ->
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
            CharacterAdapter.Mode.SELECT -> {
                if(character.selected) {
                    b.buttonSelected.setImageResource(R.drawable.checked_box)
                }
                else {
                    b.buttonSelected.setImageResource(R.drawable.unchecked_box)
                }
            }
            CharacterAdapter.Mode.PREVIEW, CharacterAdapter.Mode.UPDATE, CharacterAdapter.Mode.CONNECTSNIPPETS, CharacterAdapter.Mode.EDITSNIPPETS  -> {
                b.buttonSelected.visibility = View.GONE
                b.characterPortrait.layoutParams.height = 80
                b.characterPortrait.layoutParams.width = 80
                b.characterPortrait.requestLayout()
            }
        }
    }

}