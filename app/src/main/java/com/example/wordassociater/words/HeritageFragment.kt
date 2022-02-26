package com.example.wordassociater.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Frags
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentHeritageBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.popups.popSearchWord
import com.example.wordassociater.utils.Helper

class HeritageFragment: Fragment() {
    lateinit var b : FragmentHeritageBinding

    val rootOfLiveList = MutableLiveData<List<Word>>()
    private val synonymLiveList = MutableLiveData<List<String>>()
    companion object {
        lateinit var word: Word
        lateinit var comingFrom: Frags
        var comingFromList : WordCat? = null
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
            b.nameText.visibility = View.GONE
        }

        b.fieldBranchOf.setOnClickListener {
            popSearchWord(b.fieldBranchOf, ::wordSelectedFunc, MutableLiveData<List<Word>>())
        }

        b.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_heritageFragment_to_wordDetailedFragment)
        }

        b.btnSave.setOnClickListener {
            updateWord()
            findNavController().navigate(R.id.action_heritageFragment_to_wordDetailedFragment)

        }

    }

    private fun setRecycler() {
        synonymLiveList.value = word.synonyms
        b.synonymRecycler.initRecycler(word, synonymLiveList, ::onSynonymHeaderClicked, ::onSynonymEntered)
    }

    private fun onSynonymHeaderClicked() {
        synonymLiveList.value = word.synonyms + listOf("")
        b.synonymRecycler.adapter?.notifyDataSetChanged()
    }

    private fun onSynonymEntered(text: String) {
        val strippedWord = Helper.stripWord(text).capitalize()
        if(strippedWord != "" || text != "") {
            word.synonyms.add(strippedWord)
            synonymLiveList.value = word.synonyms
            b.synonymRecycler.adapter?.notifyDataSetChanged()
        }


    }

    private fun updateWord() {
        FireWords.update(word.id,"synonyms",word.synonyms)
        FireWords.update(word.id,"rootOf", word.rootOf)
    }



    private fun wordSelectedFunc(w: Word) {
        word.branchOf = w.text
        b.fieldBranchOf.text = w.text
        word.rootOf.add(word.id)
        FireWords.update(word.id, "rootOf", word.rootOf)
    }

}