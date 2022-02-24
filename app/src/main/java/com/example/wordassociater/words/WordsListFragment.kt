package com.example.wordassociater.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.ViewPagerFragment
import com.example.wordassociater.databinding.FragmentWordsListBinding
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.AdapterType
import com.example.wordassociater.utils.Page

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
        handleSearchBar()
        return b.root
    }

    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            ViewPagerFragment.goTopage(Page.Start)
        }
    }

    private fun handleSearchBar() {
        b.searchBar.getWords {
            if(it.isNotEmpty()) adapter.submitList(it)
            else adapter.submitList(currentList.value)
        }
    }

    private fun setRecycler() {
        adapter = WordAdapter(AdapterType.List, ::handleWordSelected, null)
        b.wordRecycler.adapter = adapter
        adapter.submitList(WordLinear.allWords.sortedBy { w -> w.text })
    }

    private fun handleDelete(word: Word) {

    }

    private fun handleWordSelected(word:Word) {
        WordDetailedFragment.word = word
        findNavController().navigate(R.id.action_ViewPagerFragment_to_wordDetailedFragment)
    }

    private fun setObserver() {
        currentList.observe(b.root.context as LifecycleOwner) {
            adapter.submitList(it.sortedBy { w -> w.text }.reversed().sortedBy { w -> w.used }.reversed())
            b.wordRecycler.scrollToPosition(0)
        }
    }



}