package com.example.wordassociater.strain_list_fragment

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.connect_strains_fragment.ConnectStrainsFragment
import com.example.wordassociater.databinding.HolderStrainBinding
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.firestore.FireStrains
import com.example.wordassociater.start_fragment.WordLinear

class StrainHolder(val b: HolderStrainBinding): RecyclerView.ViewHolder(b.root) {
    lateinit var strain: Strain
    private val strainTrigger = MutableLiveData<Unit>()

    companion object {
        var selectedStrain: Strain? = null
    }


    fun onBind(strain: Strain) {
        this.strain = strain
        setStrain()
        setClickListener()
    }

    private fun setStrain() {
        b.contentPreview.text = strain.content
        b.strainLayerButton.currentLayer.value = strain.connectionLayer
        setAssociatedWords()
        setHeader()
        setRecycler()
        setObserver()
    }

    private fun setObserver() {
        strainTrigger.observe(b.root.context as LifecycleOwner) {
            if(selectedStrain == strain) {
                b.root.setBackgroundResource(R.color.gold)
            } else {
                b.root.setBackgroundResource(R.color.white)
            }
        }
    }

    private fun setAssociatedWords() {
        var wordList = ""
        var wordRow = ""
        for(w: Word in strain.getWords()) {
            if(wordRow.length < 25) {
                wordRow += "${w.text}, "
                wordList = wordRow
            }
            else {
                wordRow += "\n"
                wordList += wordRow
                wordRow = ""
            }
        }
        b.associatedWordsStrain.text = wordList
    }

    private fun setHeader() {
        b.headerText.text = strain.header
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            WordLinear.wordList = strain.getWords() ?: mutableListOf()
            StrainListFragment.openStrain.postValue(strain)
        }

        b.connectBtn.setOnClickListener {
            when (selectedStrain) {
                strain -> {
                    selectedStrain = null
                    strainTrigger.value = Unit
                }
                null -> {
                    selectedStrain = strain
                    strainTrigger.value = Unit
                }
                else -> {
                    connectStrains(selectedStrain!!, strain)
                }
            }

        }
    }

    private fun connectStrains(strainOne: Strain, strainTwo: Strain) {
        if(!strainOne.connectionsList.contains(strainTwo.id)) {
            strainOne.connectionsList.add(strainTwo.id)
            strainTwo.connectionsList.add(strainOne.id)
            FireStrains.update(strainTwo, b.root.context)
            FireStrains.update(strainOne, b.root.context)
        }
        ConnectStrainsFragment.strainList.add(strainOne)
        ConnectStrainsFragment.strainList.add(strainTwo)
        selectedStrain = null
        StrainListFragment.navController.navigate(R.id.action_readFragment_to_connectStrainsFragment2)
    }

    private fun setRecycler() {
        b.characterRecycler.visibility = if(strain.characterList.isNotEmpty()) View.VISIBLE else View.GONE
        val adapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        b.characterRecycler.adapter = adapter
        adapter.submitList(strain.characterList)
    }
}