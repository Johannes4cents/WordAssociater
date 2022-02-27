package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarTopBarBinding

class TopBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarTopBarBinding.inflate(LayoutInflater.from(context), this, true)
    val characterImage = b.btnCharacter
    val nuwImage = b.btnNuws
    val wordsImage = b.btnWord
    val dramaImage = b.btnDrama
    private var selectedView: MutableLiveData<View> = MutableLiveData<View>()
    private var showIconSelected = false

    init {
        setObserver()
    }

    fun showIconSelection() {
        showIconSelected = true
    }

    fun showLeftText(text: String) {
        b.btnBack.visibility = View.GONE
        b.leftText.visibility = View.VISIBLE
        b.leftText.text = text

    }

    fun setNuwButton(func: () -> Unit) {
        b.btnNuws.setOnClickListener {
            selectedView.value = b.btnNuws
            func()
        }
    }

    fun setNuwIconAndVisibility(icon: Int, visible: Boolean) {
        b.btnNuws.setImageResource(icon)
        b.btnNuws.visibility = if(visible) View.VISIBLE else View.GONE
    }

    fun setBackButton(func: () -> Unit) {
        b.btnBack.setOnClickListener {
            selectedView.value = b.btnBack
            func()
        }
    }

    fun setBackIconAndVisibility(icon: Int, visible: Boolean) {
        b.btnBack.setImageResource(icon)
        b.btnBack.visibility = if(visible) View.VISIBLE else View.GONE
    }

    fun setSaveButton(func: () -> Unit) {
        b.btnSave.setOnClickListener {
            selectedView.value = b.btnSave
            func()
        }
    }

    fun setSaveIconAndVisibility(icon: Int, visible: Boolean) {
        b.btnSave.setImageResource(icon)
        b.btnSave.visibility = if(visible) View.VISIBLE else View.GONE
    }

    fun setCharacterButton(func: () -> Unit) {
        b.btnCharacter.setOnClickListener {
            selectedView.value = b.btnCharacter
            func()
        }
    }

    fun setCharacterIconAndVisibility(icon: Int, visible: Boolean) {
        b.btnCharacter.setImageResource(icon)
        b.btnCharacter.visibility = if(visible) View.VISIBLE else View.GONE
    }

    fun setWordButton(func: () -> Unit) {
        b.btnWord.setOnClickListener {
            selectedView.value = b.btnWord
            func()
        }
    }

    fun setWordIconAndVisibility(icon: Int, visible: Boolean) {
        b.btnWord.setImageResource(icon)
        b.btnWord.visibility = if(visible) View.VISIBLE else View.GONE
    }

    fun setDramaButton(func: () -> Unit) {
        b.btnDrama.setOnClickListener {
            selectedView.value = b.btnDrama
            func()
        }
    }

    fun setDramaIconAndVisibility(icon: Int, visible: Boolean) {
        b.btnDrama.setImageResource(icon)
        b.btnDrama.visibility = if(visible) View.VISIBLE else View.GONE
    }

    private fun setObserver() {
        selectedView.observe(context as LifecycleOwner) {
            val viewList = listOf(b.btnBack,nuwImage, wordsImage, dramaImage, characterImage, b.btnSave)
            if(showIconSelected) {
                for(v in viewList) {
                    v.setBackgroundColor(if(v == selectedView) b.root.resources.getColor(R.color.lightYellow) else b.root.resources.getColor(R.color.white))
                }
            }
        }
    }
}