package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarAddStuffBinding
import com.example.wordassociater.fire_classes.Character

class AddStuffBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarAddStuffBinding.inflate(LayoutInflater.from(context), this, true)
    lateinit var navController: NavController

    companion object {
        val popUpCharacterList = MutableLiveData<List<Character>>(mutableListOf())
        val selectedCharacters = mutableListOf<Character>()
        val snippetInputOpen = MutableLiveData(false)
        var newWordInputOpen = MutableLiveData(false)
    }

    init {
        setClickListener()
        setObserver()
    }


    fun handlePreviewBar(previewBar: PreviewBar) {
        b.newSnippetBar.takePreviewBar(previewBar)
        b.newSnippetBar.setPreviewBar()
    }

    private fun setClickListener() {
        b.btnNewSnippet.setOnClickListener {
            if(snippetInputOpen.value!!) b.newSnippetBar.saveSnippet()
            else snippetInputOpen.value = true
        }

        b.btnNewWord.setOnClickListener {
            newWordInputOpen.value = !newWordInputOpen.value!!
        }

    }

    private fun setObserver() {
        snippetInputOpen.observe(context as LifecycleOwner) {
            if(it == true) {
                b.newSnippetBar.visibility = View.VISIBLE
                b.btnNewSnippet.setImageResource(R.drawable.btn_save)
                b.newSnippetBar.setPreviewBar()
            }
            else {
                b.newSnippetBar.visibility = View.GONE
                b.btnNewSnippet.setImageResource(R.drawable.btn_new_snippet)
            }
        }
    }

}