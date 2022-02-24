package com.example.wordassociater.utils

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnKeyListener
import android.widget.LinearLayout
import android.widget.TableRow
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.databinding.InputFieldWordsBinding
import com.example.wordassociater.fire_classes.Nuw

class WordsInputField(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = InputFieldWordsBinding.inflate(LayoutInflater.from(context), this, true)
    var content = ""
    var takeContentFunc : ((content: String) -> Unit)? = null
    private val wordInput = MutableLiveData<List<String>>()
    val nuwList = mutableListOf<Nuw>()
    private var inputEnabled = true
    var clicked = 0

    init {
        setKeyListener()
        onChangeListener()
        setObserver()
        setClickListener()
        setOnFocusChange()
    }

    fun setContentFunc(func: (content: String) -> Unit) {
        takeContentFunc = func
    }

    private fun setClickListener() {
        b.textField.setOnClickListener {
            if(inputEnabled) showInputField()
        }

        b.inputField.setOnClickListener {
            if(clicked > 0) {
                saveInput()
                if(takeContentFunc != null) takeContentFunc?.let { it1 -> it1(content) }
                clicked = 0
            }
            else {
                clicked++
            }

        }
    }

    fun setHint(hint: String) {
        b.inputField.hint = hint
    }

    fun setCenterGravity() {
        b.inputField.gravity = Gravity.CENTER
        b.textField.gravity = Gravity.CENTER
    }

    fun setTextSize(size: Float) {
        b.inputField.textSize = size
        b.textField.textSize = size
    }

    fun setSingleLine() {
        b.inputField.isSingleLine = true
    }

    fun enableInput(enable: Boolean) {
        inputEnabled = enable
    }

    fun setTextField(content: String) {
        b.textField.text = content
        b.inputField.setText(content)
    }

    private fun showInputField() {
        b.inputField.setText(b.inputField.text.toString())
        b.textField.visibility = View.GONE
        b.inputField.visibility = View.VISIBLE
        Helper.takeFocus(b.inputField, context)
    }

    private fun saveInput() {
        b.textField.text = b.inputField.text
        b.inputField.setText(b.textField.text)
        content = b.inputField.text.toString()
        b.inputField.visibility = View.GONE
        b.textField.visibility = View.VISIBLE
        takeContentFunc?.let { it(content) }
        Helper.getIMM(b.root.context).hideSoftInputFromWindow(b.inputField.windowToken, 0)
    }

    private fun updateList() {
        var newWords = b.inputField.text.split("\\s".toRegex()).toMutableList()
        var strippedWords = mutableListOf<String>()
        for(w in newWords) {
            if(w.isNotEmpty()) strippedWords.add(Helper.stripWord(w).capitalize())
        }
        wordInput.value = strippedWords
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

    private fun setOnFocusChange() {
        Main.outsideEditClicked.observe(context as LifecycleOwner) {
            saveInput()
            Log.i("focusTest", "outside Edit Clicked")
        }
    }

    private fun onChangeListener() {
        b.inputField.doOnTextChanged { text, start, before, count ->
            if(text != null) {
                updateList()
            }
        }
    }

    private fun setObserver() {
        wordInput.observe(context as LifecycleOwner) {
            val nuwList = mutableListOf<Nuw>()

            for(string in it) {
                if(!CommonWords.commonWords.contains(string.toLowerCase())) {
                    val nuw = Nuw(
                            id = getNuwId(),
                            text = string,
                            )
                    nuwList.add(nuw)
                }
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

    fun showNuws() {
        b.inputField.visibility = View.GONE
        b.textField.visibility = View.GONE
        b.nuwTable.visibility = View.VISIBLE

        var rowIndex = 0
        val tableRowList = listOf<TableRow>(b.row1, b.row2, b.row3, b.row4)
        for(nuw in nuwList) {
            if(tableRowList[rowIndex].childCount < 5) {

            }
        }

    }

}