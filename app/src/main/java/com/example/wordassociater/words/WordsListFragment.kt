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
import com.example.wordassociater.databinding.FragmentWordsListBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.live_recycler.LiveRecycler
import com.example.wordassociater.popups.popConfirmation
import com.example.wordassociater.popups.popNewWordCat
import com.example.wordassociater.utils.LiveClass
import com.example.wordassociater.utils.Page
import com.example.wordassociater.viewpager.ViewPagerMainFragment
import com.example.wordassociater.wordcat.WordCatAdapter

class WordsListFragment: Fragment() {
    lateinit var b : FragmentWordsListBinding
    private var inSelectWords = false

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
        setClickListener()
        return b.root
    }

    private fun setTopBar() {
        b.topBar.setBtn1IconAndVisibility(R.drawable.icon_delete, true)
        b.topBar.setBtn2IconAndVisibility(R.drawable.icon_word_to_wordcat, true)
        b.topBar.setBtn3IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn4IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn5IconAndVisibility(R.drawable.icon_word, false)

        b.topBar.setLeftBtn {
            deactivateAllWordCats()
            savedScrollPosition = 0
            Main.inFragment = Frags.START
            ViewPagerMainFragment.goTopage(Page.Start)
        }

        b.topBar.setBtn1 {
            val wordCat = selectedWordCat.value
            if(wordCat != null) {
                popConfirmation(b.topBar.btn1, ::onConfirmationDelete)
            }
        }

        b.topBar.setBtn2 {
            if(!inSelectWords) {
                enterSelectWordsMode()
            }
            else {
                leaveSelectWordsMode()
            }
        }
    }

    private fun enterSelectWordsMode() {
        inSelectWords = true
        b.topBar.btn2.setImageResource(R.drawable.icon_word_to_wordcat_selected)
        b.wordRecycler.visibility = View.GONE
        b.multiSelectWordsRecycler.visibility = View.VISIBLE
        b.multiSelectWordsRecycler.initRecycler(LiveRecycler.Mode.Popup, LiveRecycler.Type.Word, ::onWordForWordCatSelected, selectedWordCat.value!!.liveWords)
        selectedWordCat.value!!.getAllWords()
        setSearchBar(selectedWordCat.value!!.getAllWords()) {
            selectedWordCat.value!!.liveWords.value = it
        }
    }

    private fun onWordForWordCatSelected(word: LiveClass) {
        selectedWordCat.value!!.takeWord(word as Word)
    }

    private fun leaveSelectWordsMode() {
        inSelectWords = false
        handleSearchBar()
        b.topBar.btn2.setImageResource(R.drawable.icon_word_to_wordcat)
        b.wordRecycler.visibility = View.VISIBLE
        b.multiSelectWordsRecycler.visibility = View.GONE
    }

    private fun onConfirmationDelete(confirmation: Boolean) {
        if(confirmation) {
            selectedWordCat.value!!.delete()
        }
    }

    private fun handleSearchBar() {
        b.searchBar.setTextColorToWhite()
        b.searchBar.setGravityToCenter()
        b.searchBar.setHint("Search")
        setSearchBar(currentList.value!!) {
            if(it.isNotEmpty()) b.wordRecycler.setLiveList(it)
            else b.wordRecycler.setLiveList(currentList.value!!)
        }
    }

    private fun setSearchBar(list: List<Word>, takeListFunc: (list: List<Word>) -> Unit) {
        b.searchBar.getWords(list) {
            takeListFunc(it)
        }
    }

    private fun setFirstList() {
        if(selectedWordCat.value == null) {
            if(Main.wordCatsList.value!!.isNotEmpty()) selectedWordCat.value = Main.wordCatsList.value!![0]
        }
    }

    private fun setRecycler() {
        b.wordCatRecycler.setupRecycler(WordCatAdapter.Type.BTN,::onCatClicked, Main.wordCatsList, headerActive = false)
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

    private fun deactivateAllWordCats() {
        for(wc in Main.wordCatsList.value!!) {
            wc.active = false
        }
    }


    private fun onCatClicked(wordCat: WordCat) {
        leaveSelectWordsMode()
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
            b.topBar.btn1.visibility = if(it.id == 1L || it.id == 2L || it.id == 0L || it.id == 3L ||it.id == 4L || it == null) View.GONE else View.VISIBLE
            b.topBar.btn2.visibility = if(it.id == 1L || it.id == 2L || it.id == 0L || it.id == 3L ||it.id == 4L || it == null) View.GONE else View.VISIBLE
        }

        DisplayFilter.observeBarColorDark(requireContext(), b.wordCatRecycler)
        DisplayFilter.observeBarColorDark(requireContext(), b.root)
    }

    private fun getCatFilteredList(id: Long): List<Word>? {
        return Main.wordsList.value?.filter { w -> w.cats.contains(id) }
    }

    private fun setClickListener() {
        b.btnNewCat.setOnClickListener {
            popNewWordCat(b.btnNewCat)
        }
    }



}