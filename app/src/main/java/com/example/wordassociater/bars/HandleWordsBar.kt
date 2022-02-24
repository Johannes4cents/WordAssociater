package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.StartFragment
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.BarWordsButtonsBinding
import com.example.wordassociater.fire_classes.Sphere
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.popups.popSelectSphere
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.words.WordLinear

class HandleWordsBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    companion object {
        var shuffleBackupWords = mutableListOf<Word.Type>()

        fun getWord(type: Word.Type): Word? {
            var tries = 0
            var randomWord : Word? = null
            while(tries < 200 && (
                            randomWord == null || WordLinear.wordList.contains(randomWord) || !Helper.checkIfWordInRightSphere(randomWord))) {
                randomWord = when(type) {
                    Word.Type.Adjective -> WordLinear.adjectivesList.random()
                    Word.Type.Person -> WordLinear.personsList.random()
                    Word.Type.CHARACTER -> WordLinear.characterList.random()
                    Word.Type.Place -> WordLinear.placesList.random()
                    Word.Type.Action -> WordLinear.actionsList.random()
                    Word.Type.Object -> WordLinear.objectsList.random()
                    Word.Type.NONE -> WordLinear.objectsList.random()
                }
                tries++
            }
            if(randomWord != null && !Helper.checkIfWordInRightSphere(randomWord)) randomWord = null
            if(WordLinear.wordList.contains(randomWord)) randomWord = null
            return randomWord
        }

    }

    var wordAmount = 6
    val b = BarWordsButtonsBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setClickListener()
        setObserver()
    }

    private fun setClickListener() {

        b.btnClearAll.setOnClickListener {
            WordLinear.wordList = WordLinear.selectedWords.toMutableList()
            handleCharacterRemoval()
            WordLinear.wordListTriger.postValue(Unit)
        }

        b.btnSpheres.setOnClickListener {
            popSelectSphere(b.btnSpheres, Main.sphereList, ::handleSelectedSphere)
        }

        b.btnRollDice.setOnClickListener {
            if(WordLinear.placesList.isNotEmpty()) {
                WordLinear.wordList = WordLinear.selectedWords.toMutableList()
                handleCharacterRemoval()
                val randomCats = listOf(
                        Word.Type.CHARACTER, Word.Type.Action,
                        Word.Type.Object, Word.Type.Person, Word.Type.Adjective,
                        Word.Type.Place, Word.Type.Object, Word.Type.Action)
                for(i in 1..wordAmount) {
                    getWord(randomCats.random())?.let { it1 -> WordLinear.wordList.add(it1) }
                }
                WordLinear.wordListTriger.postValue(Unit)
            }
        }
    }

    private fun handleSelectedSphere(sphere: Sphere) {
        sphere.selected = !sphere.selected
        if(sphere.selected) {
            if(StartFragment.selectedSphereList.value != null) {
                val newList = StartFragment.selectedSphereList.value!!.toMutableList()
                newList.add(sphere)
                StartFragment.selectedSphereList.value = newList
            }
            else {
                val newList = listOf(sphere)
                StartFragment.selectedSphereList.value = newList
            }
        }
        else {
            if(StartFragment.selectedSphereList.value != null) {
                val newList = StartFragment.selectedSphereList.value!!.toMutableList()
                newList.remove(sphere)
                StartFragment.selectedSphereList.value = newList
            }
        }

        val newMainList = Helper.getResubmitList(sphere, Main.sphereList.value!!.toMutableList())
        Main.sphereList.value = newMainList
    }

    private fun handleCharacterRemoval() {
        for(char in CharacterAdapter.selectedNameChars) {
            CharacterAdapter.selectedCharacterList.remove(char)
        }
        CharacterAdapter.selectedNameChars.clear()
    }

    private fun setObserver() {

        StartFragment.selectedSphereList.observe(context as LifecycleOwner) {
            if(it.isNotEmpty()) {
                b.btnSpheres.setImageResource(it[0].getColor())
            }
            else {
                b.btnSpheres.setImageResource(R.drawable.sphere_grey)
            }
        }
    }

}