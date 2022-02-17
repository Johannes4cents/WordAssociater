package com.example.wordassociater.words_list_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentWordsListBinding
import com.example.wordassociater.start_fragment.Word
import com.example.wordassociater.word_detailed_fragment.WordDetailedFragment

class WordsListFragment: Fragment() {
    lateinit var b : FragmentWordsListBinding

    companion object {
        lateinit var adapter: WordAdapter
        val currentList = MutableLiveData<List<Word>>()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Main.inFragment = Frags.WORDLIST
        b = FragmentWordsListBinding.inflate(inflater)
        setObserver()
        setRecycler()
        setClickListener()
        return b.root
    }

    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_wordsListFragment_to_startFragment)
        }
    }

    private fun setRecycler() {
        adapter = WordAdapter(::handleOnClick, ::handleDelete)
        b.wordRecycler.adapter = adapter
    }

    private fun handleDelete(word: Word) {

    }

    private fun handleOnClick(word:Word) {
        WordDetailedFragment.word = word
        findNavController().navigate(R.id.action_wordsListFragment_to_wordDetailedFragment)
    }

    private fun setObserver() {
        currentList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }



}