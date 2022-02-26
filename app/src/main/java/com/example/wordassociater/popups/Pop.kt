package com.example.wordassociater.popups

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import com.example.wordassociater.Main
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.PopupCharacterRecyclerBinding
import com.example.wordassociater.databinding.PopupConfirmDeletionBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.words.WordLinear

class Pop(val context: Context) {
    private val popWindow = PopupWindow(context)
    lateinit var charMode : CharacterAdapter.Mode

    companion object {
        lateinit var characterAdapter: CharacterAdapter
        var characterListSelect = Main.characterList.value?.toMutableList()
        var characterListUpdate = Main.characterList.value?.toMutableList()
    }

    fun windowSetup(view: View, fromWhere: View) {
        popWindow.isOutsideTouchable = true
        popWindow.isFocusable = true
        popWindow.contentView = view
        popWindow.showAtLocation(fromWhere, Gravity.CENTER, 0 , 0)
        popWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
    }



    fun confirmationPopUp(from: View, func: (confirmed: Boolean) -> Unit) {
        val binding = PopupConfirmDeletionBinding.inflate(LayoutInflater.from(context), null, false)
        binding.yesButton.setOnClickListener { func(true); popWindow.dismiss() }
        binding.noButton.setOnClickListener { func(false); popWindow.dismiss() }
        windowSetup(binding.root, from)
    }


    fun characterRecyclerConnectSnippets( view: View, characterList: List<Character>) {
        val binding = PopupCharacterRecyclerBinding.inflate(LayoutInflater.from(context), null, false)
        var adapter = CharacterAdapter(CharacterAdapter.Mode.CONNECTSNIPPETS, characterList)
        binding.characterRecycler.adapter = adapter
        adapter.submitList(Main.characterList.value)
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

        binding.clearAllBtn.setOnClickListener {
            CharacterAdapter.selectedCharacterList.clear()
            CharacterAdapter.selectedNameChars.clear()
            WordLinear.deselectWords()
            CharacterAdapter.characterListTrigger.value = Unit
        }
    }

    private fun handleAdapterModes() {
        when(charMode) {
            CharacterAdapter.Mode.UPDATE -> {
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