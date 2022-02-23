package com.example.wordassociater.utils

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnKeyListener
import android.widget.LinearLayout
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.InputFieldWordsBinding
import com.example.wordassociater.fire_classes.Nuw

class WordsInputField(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = InputFieldWordsBinding.inflate(LayoutInflater.from(context), this, true)
    private val nuws = MutableLiveData<List<String>>()

    init {
        setKeyListener()
        onChangeListener()
        setObserver()
    }

    private fun setClickListener() {
        b.textField.setOnClickListener {
            showInputField()
        }
    }

    fun showInputField() {
        b.textField.visibility = View.GONE
        b.inputField.visibility = View.VISIBLE
        b.inputField.isFocusable = true
        b.inputField.isFocusableInTouchMode = true
        b.inputField.requestFocus()
        Helper.getIMM(context).showSoftInput(b.inputField, 0)
    }

    private fun updateList() {
        var newWords = b.inputField.text.split("\\s".toRegex()).toMutableList()
        var strippedWords = mutableListOf<String>()
        for(w in newWords) {
            if(w.isNotEmpty()) strippedWords.add(Helper.stripWord(w).capitalize())
        }
        nuws.value = strippedWords
        b.textField.text = b.inputField.text
    }

    private fun setKeyListener() {
        b.inputField.setOnKeyListener(OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                updateList()
                return@OnKeyListener true
            }
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                Helper.toast("list is empty", context)
                updateList()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun onChangeListener() {
        b.inputField.doOnTextChanged { text, start, before, count ->
            if(text != null) {
                updateList()
            }
        }
    }

    private fun setObserver() {
        nuws.observe(context as LifecycleOwner) {
            val nuwList = mutableListOf<Nuw>()

            for(string in it) {
                val nuw = Nuw(
                        id = getNuwId(),
                        text = string,

                )
            }
        }
    }

    private fun getNuwId(): Long {
        var id: Long = 1
        while(Nuw.idList.contains(id)) {
            id = (1..100000000).random().toLong()
        }
        Nuw.idList.add(id)
        return id
    }

}