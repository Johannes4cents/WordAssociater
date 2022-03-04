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
import com.example.wordassociater.databinding.PopupConfirmDeletionBinding

class Pop(val context: Context) {
    private val popWindow = PopupWindow(context)

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


}