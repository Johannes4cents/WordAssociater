package com.example.wordassociater.utils

import android.content.Context
import android.graphics.Typeface
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnKeyListener
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.InputFieldWordsBinding
import com.example.wordassociater.fire_classes.Nuw
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.StoryPart
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.firestore.*
import java.util.*

class WordsInputField(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = InputFieldWordsBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var oldNuws: List<Nuw>
    private var storyPart: StoryPart? = null
    var content = ""
    private var onTypingFunc : ((content: String) -> Unit)? = null
    private var onEnterFunc: ((content: String) -> Unit)? = null
    private var nuwList = MutableLiveData<List<Nuw>?>()
    private var inputEnabled = true
    private var nuwsOpen = false
    private var hideOnEnter = false
    private var nuwInput = true
    private var checkOutsideEditClick = true

    private var twiceClickSafety = false
    private var firstClick = true
    var clicked = 0

    init {
        setKeyListener()
        onChangeListener()
        setClickListener()
        setOnFocusChange()
    }

    fun setContentFunc(func: (content: String) -> Unit) {
        onTypingFunc = func
    }

    fun setStoryPart(storyPart: StoryPart) {
        this.storyPart = storyPart
    }

    fun setHintInTextField(hint: String) {
        b.textField.text = hint
    }

    fun setInputTypeNumbers() {
        b.inputField.inputType = InputType.TYPE_CLASS_NUMBER
    }

    fun setToRobotoBold() {
        val typeFace: Typeface? = ResourcesCompat.getFont(b.root.context, R.font.roboto_bold)
        b.inputField.typeface = typeFace
    }

    fun setTextColorToWhite() {
        b.inputField.setTextColor(b.root.resources.getColor(R.color.white))
        b.textField.setTextColor(b.root.resources.getColor(R.color.white))
        b.inputField.setHintTextColor(b.root.resources.getColor(R.color.white))
    }

    fun setInputHint(hint: String) {
        b.inputField.hint = hint
    }

    fun setMaxInput(maxInput: Int) {
        val inputFilter = InputFilter.LengthFilter(maxInput)
        b.inputField.filters = arrayOf(inputFilter)
    }

    fun disableNuwInput() {
        nuwInput = false
    }

    fun disableOutSideEditClickCheck() {
        checkOutsideEditClick = false
    }

    fun setBgToLite() {
        b.root.setBackgroundColor(b.root.context.resources.getColor(R.color.snippetsLite))
    }

    private fun setClickListener() {
        b.textField.setOnClickListener {
            if(inputEnabled) showInputField()
        }

        b.inputField.setOnClickListener {
            if(clicked > 0) {
                if(twiceClickSafety && firstClick) {
                    firstClick = false
                }
                else {
                    hideInput()
                    if(onTypingFunc != null) onTypingFunc?.let { takeContFunc -> takeContFunc(content) }
                    clicked = 0
                    firstClick = true
                }
            }
            else {
                clicked++
            }
        }

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

    fun enableTwiceClickSafety() {
        twiceClickSafety = true
    }

    fun setTextField(content: String) {
        b.textField.text = content
        b.inputField.setText(content)
        if(nuwInput) oldNuws = createNuws()
    }

    fun hideOnEnter() {
        hideOnEnter = true
        b.inputField.isSingleLine = true
    }

    fun getNuwsForPopup(takeNuwsFunc: (
            nuwsList: MutableLiveData<List<Nuw>?>,
            onUpgradeClicked: (nuw: Nuw) -> Unit,
            onDirtClicked: (nuw: Nuw) -> Unit,
            onPotatClicked: (nuw: Nuw) -> Unit
    ) -> Unit) {
        takeNuwsFunc(nuwList, ::onNuwUpgradeClicked,::onDirtClicked, ::onPotatoClicked)
    }

    fun updateNuwsList() {
        nuwList.value = createNuws()
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

    fun hideInput() {
        b.textField.text = b.inputField.text
        b.inputField.setText(b.textField.text)
        content = b.inputField.text.toString()
        b.inputField.visibility = View.GONE
        b.textField.visibility = View.VISIBLE
        onTypingFunc?.let { it(content) }
        onEnterFunc?.let { it(content) }
        Helper.getIMM(b.root.context).hideSoftInputFromWindow(b.inputField.windowToken, 0)
    }

    var oldListLength = 0
    var newListLength = 0
    private fun updateList() {
        val contentList = getContentToList(b.inputField.text.toString())
        newListLength = contentList.count()
        b.textField.text = b.inputField.text
        content = b.inputField.text.toString()
        if(newListLength != oldListLength) {
            if(nuwInput) {
                nuwList.value = createNuws()
            }
            oldListLength = newListLength
        }
    }

    private fun getContentToList(content: String): List<String> {
        val newWords = content.split("\\s".toRegex()).toMutableList()
        val strippedWords = mutableListOf<String>()

        for(w in newWords) {
            if(w.isNotEmpty()) strippedWords.add(Helper.stripWordLeaveWhiteSpace(w))
        }

        //generate ComboNuws
        val comboList = generateComboNuws(newWords)

        return strippedWords + comboList
    }

    private fun generateComboNuws(newWords: MutableList<String>): List<String> {
        val comboNuws = mutableListOf<String>()
        if(Main.commonWordsVeryGerman.value != null) {
            for(string in newWords) {
                val previousString = if(newWords.indexOf(string) != 0) newWords[newWords.indexOf(string) - 1] else ""
                val cleanStringCurrent = Helper.stripWordLeaveWhiteSpace(string)
                val cleanStringPrevious = Helper.stripWordLeaveWhiteSpace(previousString)

                val previousWordCWCheck = Main.getCommonWordType(Language.German, cleanStringPrevious)
                val currentWordCWCheck = Main.getCommonWordType(Language.German, cleanStringCurrent)

                if(
                        previousWordCWCheck != CommonWord.Type.Very && currentWordCWCheck != CommonWord.Type.Very &&
                        !previousString.contains(".") && !string.contains(".") &&
                        !previousString.contains(",") && !string.contains(",")
                ) {
                    val comboWord = "$cleanStringPrevious $cleanStringCurrent"
                    comboNuws.add(comboWord)
                    }
                }
            }
        return comboNuws
    }

    private fun setKeyListener() {
        b.inputField.setOnKeyListener(OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                updateList()
                if(hideOnEnter) hideInput()
                
                if(onEnterFunc != null) {
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
            if(checkOutsideEditClick) {
                if(onEnterFunc != null) onEnterFunc!!(content)
                if(onTypingFunc != null) onTypingFunc!!(content)
                hideInput()
            }
        }
    }

    private fun onChangeListener() {
        b.inputField.doOnTextChanged { text, start, before, count ->
            if(text != null) {
                updateList()
            }
        }
    }

    private fun createNuws(): List<Nuw> {
        val newNuws = mutableListOf<Nuw>()
        if(nuwInput) {
            for(string in getContentToList(content)) {
                if(Main.getCommonWord(Language.German, string, CommonWord.Type.Very) == null) {
                    val nuw = Nuw.getNuw(string)
                    val word = nuw.checkIfWordExists()
                    if(word != null) {
                        nuw.isWord = true
                        nuw.usedAmount = word.used
                    }

                    if(storyPart != null) {
                        for(w in storyPart!!.getWords()) {
                            if(Helper.stripWordLeaveWhiteSpace(w.text) == nuw.text || w.getFamStrings().contains(nuw.text)) {
                                nuw.isUsed = true
                            }
                        }
                    }
                    var alreadyThere = false

                    for(n in newNuws) {
                        if(n.text == nuw.text) {
                            alreadyThere = true
                            break
                        }
                    }
                    if(!alreadyThere) newNuws.add(nuw)
                }
            }
        }


        return newNuws
    }

    fun saveNuws() {
        if(nuwList.value != null) {
            for(nuw in nuwList.value!!) {
                val existingNuw = Main.getNuw(nuw.text)
                if(existingNuw != null) {
                    if(!storyPart!!.nuwList.contains(nuw.id)) {
                        updateAlreadyExistingNuw(nuw)
                    }
                }
                else {
                    saveNewNuwToDb(nuw)
                }
            }
        }
    }

    private fun saveNewNuwToDb(nuw: Nuw) {
        nuw.usedIn.add(storyPart!!.id)
        if(!storyPart!!.nuwList.contains(nuw.id))storyPart!!.nuwList.add(nuw.id)
        FireNuws.add(nuw)
    }

    private fun updateAlreadyExistingNuw(nuw: Nuw) {
        if(!nuw.isWord) {
            nuw.usedIn.add(storyPart!!.id)
            nuw.usedAmount += 1
            storyPart!!.nuwList.add(nuw.id)
            when(storyPart) {
                is Snippet -> FireSnippets.update(storyPart!!.id, "nuwList", storyPart!!.nuwList)
            }
            FireNuws.update(nuw.id, "usedIn", nuw.usedIn)
            FireNuws.update(nuw.id, "usedAmount", nuw.usedAmount)
        }
    }

    private fun onPotatoClicked(nuw:Nuw) {
        val commonWord = CommonWord(nuw.text, Language.German, CommonWord.Type.Somewhat)
        FireCommonWords.add(commonWord)
        val newNuwList = nuwList.value!!.toMutableList()
        newNuwList.remove(nuw)
        nuwList.value = newNuwList
    }

    private fun onDirtClicked(nuw:Nuw) {
        val commonWord = CommonWord(nuw.text, Language.German, CommonWord.Type.Very)
        FireCommonWords.add(commonWord)
        val newNuwList = nuwList.value!!.toMutableList()
        newNuwList.remove(nuw)
        nuwList.value = newNuwList

    }

    private fun onNuwUpgradeClicked(nuw: Nuw) {
        nuw.upgradeToWord()
        nuwList.value = Helper.getResubmitList(nuw, nuwList.value!!)
    }

    private fun handleWordDeselectedDuringEdit(word: Word) {
        if(nuwList.value != null) {
            for(nuw in nuwList.value!!) {

            }
        }
    }

}