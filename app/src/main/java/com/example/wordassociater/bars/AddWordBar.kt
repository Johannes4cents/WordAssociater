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
import com.example.wordassociater.fire_classes.*
import com.example.wordassociater.firestore.*
import com.example.wordassociater.popups.popSelectSphere
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.Helper.getIMM
import com.example.wordassociater.words.WordLinear

class AddWordBar(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    val b = BarAddWordBinding.inflate(LayoutInflater.from(context), this, true)
    lateinit var selectedWordCat: WordCat
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
        setSelectedWordCat()

    }

    private fun setSelectedWordCat() {
        for(wc in Main.wordCatsList.value!!) {
            if(wc.id == 0L) selectedWordCat = wc
        }
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

    private fun addWord() {
        if(b.wordInput.text.isNotEmpty() ) {
            newWord.text = Helper.stripWordLeaveWhiteSpace(b.wordInput.text.toString())
            if(!newWord.cats.contains(selectedWordCat.id)) newWord.cats.add(selectedWordCat.id)
            newWord.id = FireStats.getWordId()

            if(takesWordFunc == null) {
                val existingWord = Main.getWordByText(newWord.text)
                if(existingWord == null) {
                    if(newWord.text != "Any" && newWord.text != "any") {
                        //handle special wordCats
                        val connectId = FireStats.getConnectId()
                        handleNewCharacter(connectId, newWord)
                        handleNewLocation(connectId, newWord)
                        handleNewItem(connectId, newWord)
                        handleNewEvent(connectId, newWord)

                        handleWordLinear(newWord)
                        makeFam(newWord)
                        FireWords.add(newWord, context)
                        AddStuffBar.newWordInputOpen.value = false
                    }
                    else {
                        Helper.toast("Add any word. It can be any but this one", context)
                    }
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

    private fun makeFam(word: Word) {
        val fam = Fam(FireStats.getFamNumber(), word.text)
        word.famList.add(fam.id)
        fam.word = word.id
        fam.main = true
        FireFams.add(fam)
    }

    private fun handleNewLocation(connectId: Long, newWord: Word) {
        if(selectedWordCat.type == WordCat.Type.Location) {
            if(b.wordInput.text.toString() != "Any" && b.wordInput.text.toString() != "any") {
                val location = Location(
                        id =  FireStats.getSnippetPartId(),
                        name = Helper.stripWordLeaveWhiteSpace(b.wordInput.text.toString()),
                        connectId = connectId)

                location.wordList = Word.convertToIdList(WordLinear.selectedWords)
                FireLocations.add(location, context)
                newWord.connectId = connectId
            }
            else Helper.toast("When you imagine any location - don't just imagine ANY location", context)

        }
    }

    private fun handleNewItem(connectId: Long, newWord: Word) {
        if(selectedWordCat.type == WordCat.Type.Item) {
            if(b.wordInput.text.toString() != "Any" && b.wordInput.text.toString() != "any") {
                val item = Item(
                        id =  FireStats.getSnippetPartId(),
                        name = Helper.stripWordLeaveWhiteSpace(b.wordInput.text.toString()),
                        connectId = connectId)

                item.wordsList = Word.convertToIdList(WordLinear.selectedWords)
                FireItems.add(item, context)
                newWord.connectId = connectId
            }
            else Helper.toast("If I give you any amount of money, wont you choose any item?", context)

        }
    }

    private fun handleNewEvent(connectId: Long, newWord: Word) {
        if(selectedWordCat.type == WordCat.Type.Event) {
            if(Helper.stripWordLeaveWhiteSpace(b.wordInput.text.toString()) != "Any") {
                val event = Event(
                        id =  FireStats.getSnippetPartId(),
                        name = Helper.stripWordLeaveWhiteSpace(b.wordInput.text.toString()),
                        connectId = connectId)

                event.wordList = Word.convertToIdList(WordLinear.selectedWords)

                FireEvents.add(event, context)
                newWord.connectId = connectId
            }
            else Helper.toast("Any event can happen, but which one's on your mind?", context)
        }
    }

    private fun handleNewCharacter(connectId: Long, newWord: Word) {
        if(selectedWordCat.type == WordCat.Type.Character) {
            if(b.wordInput.text.toString() != "Any" && b.wordInput.text.toString() != "any") {
                val character = Character(
                        id =  FireStats.getSnippetPartId(),
                        name = b.wordInput.text.toString(),
                        connectId = connectId)
                FireChars.add(character, context)
                newWord.connectId = connectId
            }
            else Helper.toast("A girl says a name. Any name but that one", context)

        }
    }

    private fun handleWordLinear(newWord: Word) {
        WordLinear.wordList.add(newWord)
        WordLinear.wordListTrigger.postValue(Unit)
    }


}