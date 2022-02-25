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
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.firestore.FireWordCats
import com.example.wordassociater.popups.popColorPicker
import com.example.wordassociater.utils.AdapterType
import com.example.wordassociater.utils.Page
import com.example.wordassociater.wordcat.WordCatAdapter

class WordsListFragment: Fragment() {
    lateinit var b : FragmentWordsListBinding
    private var selectedWordCat = MutableLiveData<WordCat>()

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
        setFirstList()
        setClickListener()
        handleSearchBar()
        return b.root
    }

    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            ViewPagerFragment.goTopage(Page.Start)
        }

        b.btnColorPicker.setOnClickListener {
            popColorPicker(b.btnColorPicker, ::onColorSelected)
        }
    }

    private fun handleSearchBar() {
        b.searchBar.getWords {
            if(it.isNotEmpty()) adapter.submitList(it)
            else adapter.submitList(currentList.value)
        }
    }

    private fun setFirstList() {
        selectedWordCat.value = Main.wordCatsList.value!![0]
        handleColorBtnImage()
    }

    private fun setRecycler() {
        b.wordCatRecycler.setupRecycler(WordCatAdapter.Type.BTN,::onCatClicked, Main.wordCatsList)
        b.wordCatRecycler.setHeader(false)
        adapter = WordAdapter(AdapterType.List, ::handleWordSelected, null)
        b.wordRecycler.adapter = adapter
        adapter.submitList(Main.wordsList.value!!.toMutableList().sortedBy { w -> w.text })
    }

    private fun handleDelete(word: Word) {
        word.delete()
    }

    private fun onColorSelected(color: WordCat.Color) {
        if(selectedWordCat.value != null) {
            FireWordCats.update(selectedWordCat.value!!.id, "color", color)
            selectedWordCat.value!!.color = color
        }

    }

    private fun handleColorBtnImage() {
        b.btnColorPicker.setImageResource(selectedWordCat.value!!.getBg())
    }

    private fun onCatClicked(wordCat: WordCat) {
        selectedWordCat.value = wordCat
    }

    private fun handleWordSelected(word:Word) {
        WordDetailedFragment.word = word
        findNavController().navigate(R.id.action_ViewPagerFragment_to_wordDetailedFragment)
    }

    private fun setObserver() {
        currentList.observe(b.root.context as LifecycleOwner) {
            if(it != null) adapter.submitList(it.sortedBy { w -> w.text }.reversed().sortedBy { w -> w.used }.reversed())
            b.wordRecycler.scrollToPosition(0)
        }

        selectedWordCat.observe(context as LifecycleOwner) {
            val submitList = Main.wordsList.value?.filter { w -> w.cats.contains(it.id) }
            currentList.value = submitList
            handleColorBtnImage()
        }
    }



}