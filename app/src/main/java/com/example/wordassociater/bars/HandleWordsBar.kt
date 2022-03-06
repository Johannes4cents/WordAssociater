package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.StartFragment
import com.example.wordassociater.databinding.BarWordsButtonsBinding
import com.example.wordassociater.fire_classes.Sphere
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.popups.popSelectSphere
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.words.WordLinear

class HandleWordsBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {

    val b = BarWordsButtonsBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setClickListener()
        setObserver()
    }

    private fun setClickListener() {

        b.btnClearAll.setOnClickListener {
            WordLinear.wordList = WordLinear.selectedWords.toMutableList()

            WordLinear.wordListTrigger.postValue(Unit)
        }

        b.btnSpheres.setOnClickListener {
            popSelectSphere(b.btnSpheres, Main.sphereList, ::handleSelectedSphere)
        }

        b.btnRollDice.setOnClickListener {
            rollDice()
        }
    }

    private fun rollDice() {
        if(Main.wordsList.value!!.isNotEmpty()) {
            WordLinear.wordList = WordLinear.selectedWords.toMutableList()
            val activeSpecialCats = Main.wordCatsList.value!!.filter { wc -> wc.active && wc.type != WordCat.Type.Other && wc.type != WordCat.Type.Any }
            val activeWordCats = Main.wordCatsList.value!!.filter { wc -> wc.active && wc.type == WordCat.Type.Other }
            val anyWords = Main.getWordCat(0)!!
            var wordsDispersed = 0
            var tries = 0
            var specialAdded = false
            var activeWordsCatsAmount = 0

            while(wordsDispersed < 6 && tries < 200) {
                if(!specialAdded) {
                    val word = WordLinear.getWord(activeSpecialCats.random())
                    if(word != null) {
                        WordLinear.wordList.add(word)
                        specialAdded = true
                        wordsDispersed++
                    }
                }
                if(activeWordsCatsAmount < 3)  {
                    val word = WordLinear.getWord(activeWordCats.random())
                    if(word != null) {
                        WordLinear.wordList.add(word);
                        activeWordsCatsAmount++
                        wordsDispersed++
                    }
                }
                else  {
                    val word = WordLinear.getWord(anyWords)
                    if(word != null) {
                        WordLinear.wordList.add(word)
                        wordsDispersed++
                    }
                }
                tries++
            }
            tries = 0
            while(wordsDispersed < 6 && tries < 200) {
                val word = WordLinear.getWord(anyWords)
                if(word != null) {
                    WordLinear.wordList.add(word)
                    wordsDispersed++
                }
                tries++
            }
            WordLinear.wordListTrigger.postValue(Unit)
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