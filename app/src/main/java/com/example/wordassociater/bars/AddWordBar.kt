package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.StartFragment
import com.example.wordassociater.databinding.BarAddWordBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Sphere
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.firestore.FireChars
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.popups.popSelectSphere
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.Helper.getIMM
import com.example.wordassociater.words.WordLinear

class AddWordBar(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    val b = BarAddWordBinding.inflate(LayoutInflater.from(context), this, true)
    var selectedWordCat = WordCat(1,"Adjective", WordCat.Color.Pink)
    var newWord = Word()
    var takesWordFunc : ((word:Word) -> Unit)? = null


    companion object {
        val selectedSphereList = MutableLiveData<List<Sphere>?>()
        lateinit var navController: NavController
    }

    init {
        handleSphereIconColor()
        setClickListener()
        setObserver()

    }

    fun handleTakesWordFunc(takesWordFunc : (word : Word) -> Unit) {
        this.takesWordFunc = takesWordFunc
    }

    private fun handleSphereIconColor() {
        val selectedSpheres = selectedSphereList.value?.filter { s -> s.selected }
        if(selectedSpheres != null && selectedSpheres.isNotEmpty()) b.btnSpheres.setImageResource(selectedSpheres[0].getColor())
        else b.btnSpheres.setImageResource(R.drawable.sphere_grey)
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

        b.saveBtn.setOnClickListener {
            addWord()
        }

        b.btnSpheres.setOnClickListener {
            popSelectSphere(b.btnSpheres, selectedSphereList, ::sphereSelectedFunc )
        }

        b.wordCatSelector.setCat() { cat ->
            selectedWordCat = cat
        }
    }

    private fun sphereSelectedFunc(sphere: Sphere) {
        sphere.selected = !sphere.selected
        if(sphere.selected) {
            val newList = selectedSphereList.value!!.toMutableList()
            val submitList = Helper.getResubmitList(sphere, newList)
            newWord.spheres.add(sphere.id)
            selectedSphereList.value = submitList

        }
        else {
            newWord.spheres.remove(sphere.id)
            val newList = StartFragment.selectedSphereList.value!!.toMutableList()
            newList.remove(sphere)
            StartFragment.selectedSphereList.value = newList
        }

    }

    private fun setObserver() {
        AddStuffBar.newWordInputOpen.observe(context as LifecycleOwner) {
            b.root.visibility = if(it) View.VISIBLE else View.GONE
            getIMM(context).hideSoftInputFromWindow(b.wordInput.windowToken, 0)
            b.wordInput.setText("")
        }

        selectedSphereList.observe(context as LifecycleOwner) {
            handleSphereIconColor()
        }
    }

    private fun setCatIcon() {
        val wordCats = Main.wordCatsList.value
        if(wordCats != null && wordCats.count() > 0) selectedWordCat = wordCats[0]
    }

    private fun addWord() {
        if(b.wordInput.text.isNotEmpty()) {
            newWord.text = b.wordInput.text.toString()
            if(!newWord.cats.contains(selectedWordCat.id)) newWord.cats.add(selectedWordCat.id)
            newWord.id = FireStats.getWordId()

            if(takesWordFunc == null) {
                if(!Helper.checkIfWordExists(newWord, context)) {
                    val connectId = FireStats.getCharConnectId()
                    handleCharacter(connectId, newWord)
                    handleWordLinear(newWord)
                    FireWords.add(newWord, context)
                    AddStuffBar.newWordInputOpen.value = false

                }
            }
            else {
                takesWordFunc!!(newWord)
            }
        }
        else {
            AddStuffBar.newWordInputOpen.value = false
        }
    }
    private fun handleCharacter(connectId: Long, newWord: Word) {
        if(selectedWordCat.name == "Character") {
            val character = Character(
                    id =  FireStats.getCharId(),
                    name = b.wordInput.text.toString(),
                     connectId = connectId)
            FireChars.add(character, context)
            newWord.connectId = connectId
        }
    }

    private fun handleWordLinear(newWord: Word) {
        WordLinear.wordList.add(newWord)
        WordLinear.wordListTrigger.postValue(Unit)
    }


}