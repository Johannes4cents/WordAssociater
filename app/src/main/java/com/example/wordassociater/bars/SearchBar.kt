package com.example.wordassociater.bars

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarSearchBinding
import com.example.wordassociater.utils.Helper

@SuppressLint("AppCompatCustomView")
class SearchBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarSearchBinding.inflate(LayoutInflater.from(context), this, true)
    val searchWords = MutableLiveData<List<String>>()
    var orMode = true

    init {
        setKeyListener()
        onChangeListener()
        setClickListener()
    }

    private fun setClickListener() {
        b.andOrSwitch.setOnClickListener {
            orMode = !orMode
            b.andOrSwitch.setImageResource(if(orMode) R.drawable.sign_or else R.drawable.sign_and)
        }
    }

    private fun setKeyListener() {
        b.searchStrainsInput.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                updateList()
                return@OnKeyListener true
            }
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                updateList()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun onChangeListener() {
        b.searchStrainsInput.doOnTextChanged { text, start, before, count ->
            if(text != null) {
                if(text.isNotEmpty()) {
                    updateList()
                }
            }
        }
    }

    private fun updateList() {
        var newWords = b.searchStrainsInput.text.split("\\s".toRegex()).toMutableList()
        var strippedWords = mutableListOf<String>()
        for(w in newWords) {
            if(w.isNotEmpty()) strippedWords.add(Helper.stripWord(w))
        }
        searchWords.value = strippedWords
    }

}