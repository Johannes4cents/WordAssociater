package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarAddWordBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.firestore.FireChars
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.Helper.getIMM
import com.example.wordassociater.words.WordLinear
import com.google.android.gms.common.util.CollectionUtils.listOf

class AddWordBar(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

    val b = BarAddWordBinding.inflate(LayoutInflater.from(context), this, true)
    var selectedType = Word.Type.Adjective
    var newWord = ""
    var unfolded = MutableLiveData<Boolean>()
    var takesWordFunc : ((word:Word) -> Unit)? = null


    init {
        setClickListener()
        setUpSpinner()
        handleFolding()
        unfolded.value = false
    }

    fun handleTakesWordFunc(takesWordFunc : (word : Word) -> Unit) {
        this.takesWordFunc = takesWordFunc
        unfolded.value = true
        unfolded.value = false
    }

    private fun setClickListener() {
        b.wordInput.setOnClickListener {
            b.wordInput.isFocusableInTouchMode = true
            b.wordInput.isFocusable = true
            b.wordInput.requestFocus()
            getIMM(context).showSoftInput(b.wordInput, 0)
        }

        b.wordInput.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                addWord()
                getIMM(context).hideSoftInputFromWindow(b.wordInput.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })

        b.unfoldSaveBtn.setOnClickListener {
            if(unfolded.value!!) {
                addWord()
            }
            else {
                unfolded.value = true
            }
        }

    }

    private fun handleFolding() {
        unfolded.observe(context as LifecycleOwner) {unfolded ->
            b.unfoldSaveBtn.setImageResource(if(unfolded) R.drawable.btn_add_word else R.drawable.btn_unfold)
            b.foldLineal.visibility = if(unfolded) View.VISIBLE else View.GONE
            if(takesWordFunc == null )b.openWordsFragmentButton.visibility = if(unfolded) View.GONE else View.VISIBLE
            else b.openWordsFragmentButton.visibility = View.GONE
            b.standInView.visibility = if(unfolded) View.GONE else View.VISIBLE
        }
    }

    private fun setUpSpinner() {
        val optionList = listOf<Word.Type>(Word.Type.Adjective, Word.Type.Person, Word.Type.CHARACTER, Word.Type.Action, Word.Type.Object, Word.Type.Place)
        val adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, optionList)
        b.selectTypeSpinner.adapter = adapter

        b.selectTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedType = optionList[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun addWord() {
        if(b.wordInput.text.isNotEmpty()) {
            val newWord = Word(
                    b.wordInput.text.toString(),
                    selectedType,
            id = FireStats.getWordId().toString())
            if(takesWordFunc == null) {

                if(!Helper.checkIfWordExists(newWord, context)) {
                    val newWord = Word(
                            b.wordInput.text.toString(),
                            selectedType,
                            id = FireStats.getWordId().toString())
                    val connectId = FireStats.getCharConnectId()
                    handleCharacter(connectId, newWord)
                    handleWordLinear(newWord)
                    FireWords.add(newWord)
                    hideVisuals()
                }
            }
            else {
                takesWordFunc!!(newWord)
                hideVisuals()
            }
        }
        else {
            unfolded.value = false
        }
    }
    private fun handleCharacter(connectId: Long, newWord: Word) {
        Log.i("fuckshit", "AddWordBar handleCharacter")
        if(selectedType == Word.Type.CHARACTER) {
            val character = Character(b.wordInput.text.toString(), connectId = connectId)
            FireChars.add(character, context)
            newWord.connectId = connectId
        }
    }

    private fun handleWordLinear(newWord: Word) {
        WordLinear.wordList.add(newWord)
        WordLinear.wordListTriger.postValue(Unit)
    }

    private fun hideVisuals() {
        // Visuals
        getIMM(context).hideSoftInputFromWindow(b.wordInput.windowToken, 0)
        b.wordInput.setText("")
        unfolded.value = false
    }


}