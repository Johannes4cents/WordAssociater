package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import com.example.wordassociater.databinding.PopupConfirmDeletionBinding
import com.example.wordassociater.utils.Helper

fun popConfirmation(from: View, func: (confirmed: Boolean) -> Unit) {
    val binding = PopupConfirmDeletionBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(binding.root, from, fromMiddle = true)
    binding.yesButton.setOnClickListener { func(true); pop.dismiss() }
    binding.noButton.setOnClickListener { func(false); pop.dismiss() }

}