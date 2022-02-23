package com.example.wordassociater.utils

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnKeyListener
import android.widget.LinearLayout
import android.widget.TableRow
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.InputFieldWordsBinding
import com.example.wordassociater.fire_classes.Nuw

class WordsInputField(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = InputFieldWordsBinding.inflate(LayoutInflater.from(context), this, true)
    var content = ""
    var takeContentFunc : ((content: String) -> Unit)? = null
    private val wordInput = MutableLiveData<List<String>>()
    val nuwList = mutableListOf<Nuw>()
    private var inputEnabled = true

    init {
        setKeyListener()
        onChangeListener()
        setObserver()
        setClickListener()
    }

    fun setContentFunc(func: (content: String) -> Unit) {
        takeContentFunc = func
    }

    private fun setClickListener() {
        b.textField.setOnClickListener {
            if(inputEnabled) showInputField()
        }

        b.inputField.setOnClickListener {
            saveInput()
            if(takeContentFunc != null) takeContentFunc?.let { it1 -> it1(content) }
        }
    }

    fun enableInput(enable: Boolean) {
        inputEnabled = enable
    }

    fun setTextField(content: String) {
        b.textField.text = content
    }

    fun showInputField() {
        b.textField.visibility = View.GONE
        b.inputField.visibility = View.VISIBLE
        b.inputField.setText(b.textField.text)
        Helper.takeFocus(b.inputField, context)
    }

    fun saveInput() {
        b.textField.text = b.inputField.text
        content = b.inputField.text.toString()
        b.inputField.visibility = View.GONE
        b.textField.visibility = View.VISIBLE
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