package com.example.wordassociater.words

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Frags
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentHeritageBinding
import com.example.wordassociater.fams.FamRecycler
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.popups.popFamPicker
import com.example.wordassociater.popups.popSearchWord
import com.example.wordassociater.utils.Helper
import java.util.*

class HeritageFragment: Fragment() {
    lateinit var b : FragmentHeritageBinding

    val rootOfLiveList = MutableLiveData<List<Word>>()
    private val famLiveList = MutableLiveData<List<Fam>>(listOf())
    private val stemsLiveList = MutableLiveData<List<String>>(listOf())
    companion object {
        lateinit var word: Word
        lateinit var comingFrom: Frags
        var comingFromList : WordCat? = null
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = FragmentHeritageBinding.inflate(inflater)

        setContent()
        setClickListener()
        setRecycler()

        return b.root
    }

    private fun setContent() {
        b.nameText.text = word.text
        b.fieldBranchOf.text = "None"
    }

    private fun setClickListener() {
        b.nameText.setOnClickListener {
            if(word.synonyms.isNotEmpty()) {
                popFamPicker(b.nameText, word, ::onFamPickedForNewName, word.getFams())
            }
            else {
                Helper.toast("First, create synonyms to choose from", requireContext())
            }

        }

        b.fieldBranchOf.setOnClickListener {
            popSearchWord(b.fieldBranchOf, ::wordSelectedFunc, MutableLiveData<List<Word>>())
        }

        b.btnBack.setOnClickListener {
            stemsLiveList.value = mutableListOf()
            famLiveList.value = mutableListOf()
            findNavController().navigate(R.id.action_heritageFragment_to_wordDetailedFragment)
        }

        b.btnSave.setOnClickListener {
            updateWord()
            stemsLiveList.value = mutableListOf()
            famLiveList.value = mutableListOf()
            findNavController().navigate(R.id.action_heritageFragment_to_wordDetailedFragment)

        }

        b.deleteWord.setOnClickListener {
            Pop(b.deleteWord.context).confirmationPopUp(b.deleteWord, ::onDeletionConfirmed)
        }
    }


    private fun onFamPickedForNewName(fam: Fam) {
        b.nameText.text = fam.text
        word.text = fam.text
    }

    private fun onDeletionConfirmed(confirmation: Boolean) {
        if(confirmation) {
            word.delete()
            findNavController().navigate(R.id.action_heritageFragment_to_ViewPagerFragment)
        }
    }

    private fun setRecycler() {
        famLiveList.value = word.getFams()
        b.famRecycler.initRecycler(FamRecycler.Type.List, word, famLiveList, ::onFamHeaderClicked, ::onFamEntered)

        stemsLiveList.value = word.stems
        b.stemsRecycler.initRecycler(word, stemsLiveList, ::onStemHeaderClicked, ::onStemEntered)

    }

    private fun onFamHeaderClicked() {
        val newFam = Fam(id = FireStats.getFamNumber())
        famLiveList.value = listOf(newFam) + word.getFams()
        b.famRecycler.adapter?.notifyDataSetChanged()
    }


    private fun onFamEntered(fam: Fam) {
        if(fam.text != "" && fam.text != " " && word.checkIfFamExists(fam.text) == null) {

            famLiveList.value = word.getFams()
            b.famRecycler.adapter?.notifyDataSetChanged()
        }
    }



    private fun onStemHeaderClicked() {
        Log.i("saveSte", "onSteamHeader Clicked word.synonyms are : ${word.synonyms}")
        word.stems.remove("stemHeader")
        word.stems.remove("StemHeader")
        word.stems.remove("")
        word.stems.remove(" ")
        stemsLiveList.value = word.stems + listOf("")
        b.stemsRecycler.adapter?.notifyDataSetChanged()
    }

    private fun onStemEntered(text: String) {
        Log.i("saveSte", "onStemEntered word.synonyms are : ${word.synonyms}")
        val strippedWord = Helper.stripWord(text).capitalize(Locale.ROOT)
        if(strippedWord != " " && text != "" && !word.stems.contains(strippedWord) && text != "stemHeader" && text != "StemHeader") {
            word.stems.add(strippedWord)
            stemsLiveList.value = word.stems
            b.stemsRecycler.adapter?.notifyDataSetChanged()
        }
    }

    private fun updateWord() {
        FireWords.update(word.id,"synonyms",word.synonyms)
        FireWords.update(word.id,"rootOf", word.rootOf)
        FireWords.update(word.id, "stems", word.stems)
        FireWords.update(word.id, "text", word.text)

        Log.i("saveStemsProb", " updateWord - called")
    }



    private fun wordSelectedFunc(w: Word) {
        word.branchOf = w.text
        b.fieldBranchOf.text = w.text
        word.rootOf.add(word.id)
        FireWords.update(word.id, "rootOf", word.rootOf)
    }

}