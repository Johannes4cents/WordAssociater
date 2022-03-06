package com.example.wordassociater.words

import android.os.Bundle
import android.util.Log
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
import com.example.wordassociater.databinding.FragmentWordsListBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.utils.Page
import com.example.wordassociater.viewpager.ViewPagerMainFragment
import com.example.wordassociater.wordcat.WordCatAdapter

class WordsListFragment: Fragment() {
    lateinit var b : FragmentWordsListBinding

    companion object {
        var selectedWordCat = MutableLiveData<WordCat>()
        val currentList = MutableLiveData<List<Word>>()
        private var savedScrollPosition: Int = 0
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Main.inFragment = Frags.WORDLIST
        b = FragmentWordsListBinding.inflate(inflater)
        setRecycler()
        setObserver()
        setTopBar()
        handleSearchBar()
        return b.root
    }

    private fun setTopBar() {
        b.topBar.setBtn1IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn2IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn3IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn4IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn5IconAndVisibility(R.drawable.icon_word, false)

        b.topBar.setLeftBtn {
            savedScrollPosition = 0
            Main.inFragment = Frags.START
            ViewPagerMainFragment.goTopage(Page.Start)
        }
    }

    private fun handleSearchBar() {
        b.searchBar.setGravityToCenter()
        b.searchBar.setHint("Search")
        b.searchBar.getWords(currentList.value!!) {
            if(it.isNotEmpty()) b.wordRecycler.setLiveList(it)
            else b.wordRecycler.setLiveList(currentList.value!!)
        }
    }

    private fun setFirstList() {
        if(selectedWordCat.value == null) {
            Log.i("wordCatProb", "selectedWordCat = ${selectedWordCat.value}")
            if(Main.wordCatsList.value!!.isNotEmpty()) selectedWordCat.value = Main.wordCatsList.value!![1]
        }
    }

    private fun setRecycler() {
        b.wordCatRecycler.setupRecycler(WordCatAdapter.Type.BTNALL,::onCatClicked, Main.wordCatsList)
        b.wordCatRecycler.setHeader(false)
        b.wordRecycler.initRecycler(WordRecycler.Mode.List, null, ::onWordSelected, ::onHeaderClicked)


        setFirstList()
        b.wordRecycler.setLiveList(getCatFilteredList(selectedWordCat.value!!.id)!!.sortedBy { w -> w.text }.reversed().sortedBy { w -> w.used }.reversed())
        b.wordRecycler.scrollToPosition(savedScrollPosition)
    }

    private fun handleDelete(word: Word) {
        word.delete()
    }

    private fun onHeaderClicked(wordText: String) {

    }


    private fun onCatClicked(wordCat: WordCat) {
        selectedWordCat.value = wordCat
    }

    private fun onWordSelected(word:Word) {
        WordDetailedFragment.word = word
        WordDetailedFragment.comingFromList = selectedWordCat.value
        savedScrollPosition = word.adapterPosition
        findNavController().navigate(R.id.action_ViewPagerFragment_to_wordDetailedFragment)
    }

    private fun setObserver() {
        currentList.observe(b.root.context as LifecycleOwner) {
            b.wordRecycler.scrollToPosition(savedScrollPosition)
        }

        selectedWordCat.observe(context as LifecycleOwner) {
            currentList.value = getCatFilteredList(it.id)
            getCatFilteredList(it.id)?.let { wordList -> b.wordRecycler.setLiveList(wordList) }

        }

        DisplayFilter.observeBarColorDark(requireContext(), b.wordCatRecycler)
        DisplayFilter.observeBarColorDark(requireContext(), b.root)
    }

    private fun getCatFilteredList(id: Long): List<Word>? {
        return Main.wordsList.value?.filter { w -> w.cats.contains(id) }
    }



}