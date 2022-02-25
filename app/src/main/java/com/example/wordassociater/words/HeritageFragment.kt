package com.example.wordassociater.words

import android.os.Bundle
import android.view.KeyEvent
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
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.popups.popSearchWord
import com.example.wordassociater.utils.Helper

class HeritageFragment: Fragment() {
    lateinit var b : FragmentHeritageBinding

    lateinit var synonymAdapter : WordAdapter
    lateinit var rootOfAdapter: WordAdapter

    val rootOfLiveList = MutableLiveData<List<Word>>()
    val synonymLiveList = MutableLiveData<List<Word>>()
    companion object {
        lateinit var word: Word
        lateinit var comingFrom: Frags
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentHeritageBinding.inflate(inflater)

        setContent()
        setClickListener()
        setKeyListener()
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
            Helper.takeFocus(b.inputName, requireContext())
        }

        b.fieldBranchOf.setOnClickListener {
            popSearchWord(b.fieldBranchOf, ::wordSelectedFunc, MutableLiveData<MutableList<Word>>())
        }

        b.btnAddRootOf.setOnClickListener {

        }

        b.btnAddSynonyms.setOnClickListener {

        }

        b.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_heritageFragment_to_wordDetailedFragment)
        }

    }

    private fun setRecycler() {
        b.rootOfRecycler.adapter = rootOfAdapter
        b.synonymsRecycler.adapter = synonymAdapter

        rootOfAdapter.submitList(Word.convertIdListToWord(word.rootOf))

    }

    private fun setObserver() {

    }

    private fun wordSelectedFunc(w: Word) {
        word.branchOf = w.text
        b.fieldBranchOf.text = w.text
        word.rootOf.add(word.id)
        FireWords.update(word.id, "rootOf", word.rootOf)
    }

    private fun setKeyListener() {
        b.inputName.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                word.text = b.inputName.text.toString()
                b.nameText.text = b.inputName.text
                b.inputName.text = ""
                b.inputName.visibility = View.GONE
                b.nameText.visibility = View.VISIBLE
                return@OnKeyListener true
            }
            false
        })

    }
}