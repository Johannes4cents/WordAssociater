package com.example.wordassociater.strains

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.HolderStrainBinding
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.firestore.FireStrains
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.utils.Helper

class StrainHolder(
        val b: HolderStrainBinding,
        val strainClickedFunc: (strain: Strain) -> Unit
        ): RecyclerView.ViewHolder(b.root) {
    lateinit var strain: Strain
    private val strainTrigger = MutableLiveData<Unit>()

    companion object {
        var selectedStrain: Strain? = null
    }


    fun onBind(strain: Strain) {
        this.strain = strain
        setStrain()
        setClickListener()
        setWordIcon()
    }

    private fun setWordIcon() {
        if(strain.wordList.isEmpty()) {
            b.associatedWordsStrain.visibility = View.GONE
            b.associatedWordsIcon.visibility = View.VISIBLE
        }
        else {
            b.associatedWordsStrain.visibility = View.VISIBLE
            b.associatedWordsIcon.visibility = View.GONE
        }
    }

    private fun setStrain() {
        b.textFieldId.text = strain.id.toString()
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
        b.associatedWordsStrain.text = Helper.setWordsToMultipleLines(strain.getWords())
    }

    private fun setHeader() {
        b.headerText.text = strain.header
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            strainClickedFunc(strain)
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

        b.btnDelete.setOnClickListener {
            Pop(b.btnDelete.context).confirmationPopUp(b.btnDelete, ::deleteStrainFunc)
        }

    }

    private fun deleteStrainFunc(confirmation: Boolean) {
        if(confirmation) strain.delete()
    }

    private fun connectStrains(strainOne: Strain, strainTwo: Strain) {
        if(!strainOne.connectionsList.contains(strainTwo.id)) {
            strainOne.connectionsList.add(strainTwo.id)
            strainTwo.connectionsList.add(strainOne.id)
            FireStrains.update(strainTwo.id, "connectionsList", strainTwo.connectionsList)
            FireStrains.update(strainOne.id, "connectionsList", strainOne.connectionsList)
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
        adapter.submitList(strain.getCharacters())
    }
}