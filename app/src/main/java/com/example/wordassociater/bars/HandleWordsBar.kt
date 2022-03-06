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
import com.example.wordassociater.popups.popSelectSphere
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.words.WordLinear
import com.example.wordassociater.words.WordLinear.Companion.getWord

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
            if(Main.wordsList.value!!.isNotEmpty()) {
                val activeWordCats = Main.wordCatsList.value!!.filter { wc -> wc.active }
                WordLinear.wordList = WordLinear.selectedWords.toMutableList()
                for(i in 1..6) {
                    if(activeWordCats.isNotEmpty()) {
                        getWord(activeWordCats.random())?.let { it1 -> WordLinear.wordList.add(it1) }
                    }
                    else {
                        Helper.toast("select at least one wordCat to choose from", context)
                    }

                }
                WordLinear.wordListTrigger.postValue(Unit)
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