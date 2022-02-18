package com.example.wordassociater.popups

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.NavController
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.PopupCharacterSelectorBinding
import com.example.wordassociater.utils.Helper

fun popDialogueCharacterSelector(from: View, context: Context, navController: NavController) {
    val b = PopupCharacterSelectorBinding.inflate(LayoutInflater.from(context), null, false)
    val adapter = CharacterAdapter(CharacterAdapter.Mode.MAIN)
    fun setBinding() {
        b.characterRecycler.adapter = adapter
        b.goToNewDialogueBtn.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_editDialogueFragment)
        }
    }

    setBinding()
    val popUp = Helper.getPopUp(b.root,from, context)


}