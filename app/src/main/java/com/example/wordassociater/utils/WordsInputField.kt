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
import com.example.wordassociater.fire_classes.Dialogue
import com.example.wordassociater.fire_classes.Nuw
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.firestore.*
import java.util.*

class WordsInputField(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = InputFieldWordsBinding.inflate(LayoutInflater.from(context), this, true)
    private var storyPart: StoryPart? = null
    private val tableRowList = listOf<TableRow>(b.row1, b.row2, b.row3, b.row4)
    var content = ""
    private var takeContentFunc : ((content: String) -> Unit)? = null
    private var onEnterFunc: ((content: String) -> Unit)? = null
    private val wordInput = MutableLiveData<List<String>>()
    private var nuwList = MutableLiveData<List<Nuw>?>()
    private var inputEnabled = true
    private var nuwsOpen = false
    var clicked = 0

    init {
        setKeyListener()
        onChangeListener()
        setClickListener()
        setOnFocusChange()
    }

    fun setContentFunc(func: (content: String) -> Unit) {
        takeContentFunc = func
    }

    fun setStoryPart(storyPart: StoryPart) {
        this.storyPart = storyPart
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

    fun setOnEnterFunc(onEnter: (content: String) -> Unit) {
        onEnterFunc = onEnter
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

    fun getNuwsForPopup(takeNuwsFunc: (
            nuwsList: MutableLiveData<List<Nuw>?>,
            onUpgradeClicked: (nuw: Nuw) -> Unit,
            onRedXClicked: (nuw: Nuw) -> Unit
    ) -> Unit) {
        takeNuwsFunc(nuwList, ::onNuwUpgradeClicked, ::onNuwRedXClicked)
    }

    fun getNuws(takeNuwsFunc: (nuwsList: List<Nuw>?) -> Unit) {
        takeNuwsFunc(nuwList.value)
    }

    fun resetField() {
        b.inputField.setText("")
        b.textField.text = ""
        content = ""
        Helper.getIMM(b.root.context).hideSoftInputFromWindow(b.inputField.windowToken, 0)
        b.inputField.visibility = View.GONE
        b.textField.visibility = View.VISIBLE
        nuwList.value = mutableListOf()
    }

    fun showInputField() {
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
        wordInput.value = getContentToList(b.inputField.text.toString())
        b.textField.text = b.inputField.text
        content = b.inputField.text.toString()
        createNuws()
    }

    private fun getContentToList(content: String): List<String> {
        val newWords = content.split("\\s".toRegex()).toMutableList()
        val strippedWords = mutableListOf<String>()
        for(w in newWords) {
            if(w.isNotEmpty()) strippedWords.add(Helper.stripWord(w).capitalize(Locale.ROOT))
        }
        return strippedWords
    }

    private fun setKeyListener() {
        b.inputField.setOnKeyListener(OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                updateList()
                if(onEnterFunc != null) {
                    saveInput()
                    onEnterFunc!!(content)
                }
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

    private fun createNuws() {
        val newNuws = mutableListOf<Nuw>()
        for(string in getContentToList(content)) {
            if(Main.getCommonWord(Language.German, string) == null) {
                val nuw = Nuw.getNuw(string)
                val word = nuw.checkIfWordExists()
                if(word != null) {
                    nuw.isWord = true
                    nuw.usedAmount = word.used
                }

                if(storyPart != null) {
                    for(w in storyPart!!.getWordsAsStory()) {
                        if(Helper.stripWord(w.text).capitalize(Locale.ROOT) == nuw.text || w.synonyms.contains(nuw.text)) {
                            nuw.isUsed = true
                        }
                    }
                }
                newNuws.add(nuw)
            }
        }
        nuwList.value = newNuws
    }

    fun saveNuws() {
        if(nuwList.value != null) {
            for(nuw in nuwList.value!!) {
                val existingNuw = Main.getNuw(nuw.text)
                if(existingNuw != null) {
                    if(!storyPart!!.nuwList.contains(nuw.id) && !nuw.isWord) {
                        updateAlreadyExistingNuw(nuw)
                    }
                }
                else {
                    nuw.usedIn.add(storyPart!!.id)
                    FireNuws.add(nuw)
                }
            }
        }
    }

    private fun updateAlreadyExistingNuw(nuw: Nuw) {
        if(!nuw.isWord) {
            nuw.usedIn.add(storyPart!!.id)
            nuw.usedAmount += 1
            storyPart!!.nuwList.add(nuw.id)
            when(storyPart) {
                is Snippet -> FireSnippets.update(storyPart!!.id, "nuwList", storyPart!!.nuwList)
                is Dialogue -> FireDialogue.update(storyPart!!.id, "nuwList", storyPart!!.nuwList)
            }
            FireNuws.update(nuw.id, "usedIn", nuw.usedIn)
            FireNuws.update(nuw.id, "usedAmount", nuw.usedAmount)
        }
    }

    private fun onNuwRedXClicked(nuw:Nuw) {
        val commonWord = CommonWord(nuw.text, Language.German)
        FireCommonWords.add(commonWord)
        val newNuwList = nuwList.value!!.toMutableList()
        newNuwList.remove(nuw)
        nuwList.value = newNuwList

    }

    private fun onNuwUpgradeClicked(nuw: Nuw) {
        if(nuw.checkIfWordExists() == null) {
            val word = Word()
            word.id = FireStats.getWordId()
            word.text = nuw.text
            FireWords.add(word, context)
            nuw.isWord = true
            nuwList.value = Helper.getResubmitList(nuw, nuwList.value!!)
        }
    }


}