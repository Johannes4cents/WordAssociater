package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.wordassociater.databinding.BarTopBarBinding

class TopBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarTopBarBinding.inflate(LayoutInflater.from(context), this, true)
    val characterImage = b.btnCharacter
    val nuwImage = b.btnNuws
    val wordsImage = b.btnWord
    val dramaImage = b.btnDrama


    fun setNuwButton(func: () -> Unit) {
        b.btnNuws.setOnClickListener {
            func()
        }
    }

    fun setNuwIconAndVisibility(icon: Int, visible: Boolean) {
        b.btnNuws.setImageResource(icon)
        b.btnNuws.visibility = if(visible) View.VISIBLE else View.GONE
    }

    fun setBackButton(func: () -> Unit) {
        b.btnBack.setOnClickListener {
            func()
        }
    }

    fun setBackIconAndVisibility(icon: Int, visible: Boolean) {
        b.btnBack.setImageResource(icon)
        b.btnBack.visibility = if(visible) View.VISIBLE else View.GONE
    }

    fun setSaveButton(func: () -> Unit) {
        b.btnSave.setOnClickListener {
            func()
        }
    }

    fun setSaveIconAndVisibility(icon: Int, visible: Boolean) {
        b.btnSave.setImageResource(icon)
        b.btnSave.visibility = if(visible) View.VISIBLE else View.GONE
    }

    fun setCharacterButton(func: () -> Unit) {
        b.btnCharacter.setOnClickListener {
            func()
        }
    }

    fun setCharacterIconAndVisibility(icon: Int, visible: Boolean) {
        b.btnCharacter.setImageResource(icon)
        b.btnCharacter.visibility = if(visible) View.VISIBLE else View.GONE
    }

    fun setWordButton(func: () -> Unit) {
        b.btnWord.setOnClickListener {
            func()
        }
    }

    fun setWordIconAndVisibility(icon: Int, visible: Boolean) {
        b.btnWord.setImageResource(icon)
        b.btnWord.visibility = if(visible) View.VISIBLE else View.GONE
    }

    fun setDramaButton(func: () -> Unit) {
        b.btnDrama.setOnClickListener {
            func()
        }
    }

    fun setDramaIconAndVisibility(icon: Int, visible: Boolean) {
        b.btnDrama.setImageResource(icon)
        b.btnDrama.visibility = if(visible) View.VISIBLE else View.GONE
    }
}