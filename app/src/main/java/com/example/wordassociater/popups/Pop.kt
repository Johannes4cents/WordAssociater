package com.example.wordassociater.popups

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.PopupCharacterRecyclerBinding
import com.example.wordassociater.databinding.PopupConfirmDeletionBinding
import com.example.wordassociater.databinding.PopupWordRecyclerBinding
import com.example.wordassociater.strain_list_fragment.StrainListFragment
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.story.Story
import com.example.wordassociater.utils.Helper

class Pop(val context: Context) {
    private val popWindow = PopupWindow(context)
    lateinit var charMode : CharacterAdapter.Mode

    companion object {
        lateinit var characterAdapter: CharacterAdapter
        var characterListSelect = Main.characterList.toMutableList()
        var characterListUpdate = Main.characterList.toMutableList()
    }

    private fun windowSetup(view: View, fromWhere: View) {
        popWindow.isOutsideTouchable = true
        popWindow.isFocusable = true
        popWindow.contentView = view
        popWindow.showAtLocation(fromWhere, Gravity.CENTER, 0 , 0)
        popWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))

    }

    fun wordRecycler(
            from: View,
            onClickFunc: (word: Word) -> Unit,
            btnNewWordFunc: (word:Word) -> Unit,
            liveList : MutableLiveData<MutableList<Word>>,
            addNewWordFunc: (word: Word) -> Unit) {
        val b = PopupWordRecyclerBinding.inflate(LayoutInflater.from(context), null, false)
        windowSetup(b.root, from)
        val adapter = WordPopUpAdapter(onClickFunc, btnNewWordFunc)
        b.wordRecycler.adapter = adapter
        b.addWordBar.handleTakesWordFunc(addNewWordFunc)
        liveList.observe(context as LifecycleOwner) {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            Toast.makeText(context, "new list observered", Toast.LENGTH_SHORT).show()
        }
    }

    fun characterRecycler(from: View, func: (char: Character) -> Unit) {
        val b = PopupCharacterRecyclerBinding.inflate(LayoutInflater.from(context), null, false)
        val adapter = CharacterAdapter(CharacterAdapter.Mode.EDITSNIPPETS, selectFunc = func)
        b.characterRecycler.adapter = adapter
        adapter.submitList(Main.characterList)
        popWindow.height = 1000
        popWindow.width = 900
        windowSetup(b.root, from)
    }


    fun confirmationPopUp(from: View, func: (confirmed: Boolean) -> Unit) {
        val binding = PopupConfirmDeletionBinding.inflate(LayoutInflater.from(context), null, false)
        binding.yesButton.setOnClickListener { func(true); popWindow.dismiss() }
        binding.noButton.setOnClickListener { func(false); popWindow.dismiss() }
        windowSetup(binding.root, from)
    }


    fun characterRecyclerConnectSnippets( view: View, characterList: MutableList<Character>) {
        val binding = PopupCharacterRecyclerBinding.inflate(LayoutInflater.from(context), null, false)
        var adapter = CharacterAdapter(CharacterAdapter.Mode.CONNECTSNIPPETS, characterList)
        binding.characterRecycler.adapter = adapter
        adapter.submitList(Main.characterList)
        popWindow.height = 1000
        popWindow.width = 900
        windowSetup(binding.root, view)
    }


    fun characterRecycler(view: View, toMode: CharacterAdapter.Mode) {
        charMode = toMode
        val binding = PopupCharacterRecyclerBinding.inflate(LayoutInflater.from(context), null, false)
        characterAdapter = CharacterAdapter(toMode)
        binding.characterRecycler.adapter = characterAdapter
        popWindow.height = 1000
        popWindow.width = 900
        windowSetup(binding.root, view)
        handleAdapterModes()

        binding.isStoryButton.setOnClickListener {
            Story.storyModeActive = !Story.storyModeActive
            binding.isStoryButton.setImageResource(if(Story.storyModeActive) R.drawable.icon_storymode_selected else R.drawable.icon_write_story)
        }

        binding.clearAllBtn.setOnClickListener {
            CharacterAdapter.selectedCharacterList.clear()
            CharacterAdapter.selectedNameChars.clear()
            Helper.deselectWords()
            CharacterAdapter.characterListTrigger.value = Unit
        }
    }

    private fun handleAdapterModes() {
        when(charMode) {
            CharacterAdapter.Mode.UPDATE -> {
                for(char in StrainListFragment.openStrain.value?.characterList!!) {
                    var charakter = characterListUpdate.find { c -> c.name == char.name }
                    if(charakter != null) {
                        var index = characterListUpdate.indexOf(charakter)
                        characterListUpdate[index].selected = true
                    }
                }
                characterAdapter.submitList(characterListUpdate)
            }
            CharacterAdapter.Mode.SELECT -> {
                characterAdapter.submitList(characterListSelect)
            }
            else -> {

            }
        }
    }
}