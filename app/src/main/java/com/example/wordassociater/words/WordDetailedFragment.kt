package com.example.wordassociater.words

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentWordDetailedBinding
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.Sphere
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.firestore.FireWordCats
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.popups.popConfirmation
import com.example.wordassociater.popups.popSelectSphere
import com.example.wordassociater.snippets.SnippetAdapter
import com.example.wordassociater.utils.Page
import com.example.wordassociater.viewpager.ViewPagerMainFragment

class WordDetailedFragment: Fragment() {
    lateinit var b: FragmentWordDetailedBinding
    private val selectedSpheres = MutableLiveData<List<Sphere>?>()
    private val selectedCats = MutableLiveData<List<WordCat>>()

    companion object {
        lateinit var word: Word
        lateinit var snippetAdapter: SnippetAdapter
        var comingFromList : WordCat? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentWordDetailedBinding.inflate(inflater)
        setClickListener()
        setContent()
        setRecycler()
        return b.root
    }

    private fun setContent() {
        b.wordText.text = word.text
        pickFirstWordCatColor()
        getSpheresList()
        selectSpheresInList()
        if(word.cats.isNotEmpty()) b.catColor.setImageResource(Main.getWordCat(word.cats[0])!!.getBg())
    }

    private fun makeSelectedWordCatList(): List<WordCat> {
        val wordCatList = Main.wordCatsList.value!!.toMutableList()
        for(w in wordCatList) {
            w.selected = word.cats.contains(w.id)
        }
        for(w in wordCatList) {
            Log.i("wordCat", "isSelected = ${w.selected}")
        }
        return wordCatList

    }

    private fun getSpheresList() {
        val spheres = Main.sphereList.value?.toMutableList()
        if(spheres != null) {
            for(sphere in spheres) {
                sphere.selected = false
            }
        }
        selectedSpheres.value = spheres
    }

    private fun selectSpheresInList() {
        val sphereList = selectedSpheres.value!!.toMutableList()
        for(sphere in sphereList) {
            if(word.getSphereList().contains(sphere)) sphere.selected = true
        }
        selectedSpheres.value = sphereList
    }

    private fun pickFirstWordCatColor() {
        if(word.cats.isNotEmpty()) {
            b.catColor.setImageResource(Main.getWordCat(word.cats[0])!!.getBg())
        }
        else {
            b.catColor.setImageResource(R.drawable.wordcat_bg_none)
        }
    }

    private fun setClickListener() {
        b.topBar.setLeftBtn {
            findNavController().navigate(R.id.action_wordDetailedFragment_to_ViewPagerFragment)
            if(comingFromList != null) WordsListFragment.selectedWordCat.value = comingFromList
            ViewPagerMainFragment.comingFrom = Page.Words
        }

        b.buttonSnippets.setOnClickListener {
            b.wordDetailedRecycler.adapter = snippetAdapter
            getFilteredSnippetList()
        }

        b.topBar.setBtn1 {
            WordConnectionsFragment.word = word
            findNavController().navigate(R.id.action_wordDetailedFragment_to_wordConnectionsFragment)
        }

        b.topBar.setBtn3 {
            HeritageFragment.word = word
            HeritageFragment.comingFromList = comingFromList
            findNavController().navigate(R.id.action_wordDetailedFragment_to_heritageFragment)
        }

        b.topBar.setBtn4 {
            popSelectSphere(b.topBar.btn4, selectedSpheres, ::handleSelectedSphere)
        }

        b.topBar.setRightButton {
            popConfirmation(b.topBar.btn5, ::onDeleteConfirmed)
        }

        b.topBar.setBtn5IconAndVisibility(R.drawable.icon_delete, false)
        b.topBar.setBtn2IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setRightBtnIconAndVisibility(R.drawable.icon_delete, true)

        b.topBar.setBtn1IconAndVisibility(R.drawable.icon_word_connection, true)
        b.topBar.setBtn3IconAndVisibility(R.drawable.icon_heritage, true)
        b.topBar.setBtn4IconAndVisibility(R.drawable.sphere_blue, true)

        b.catColor.setOnClickListener {
            selectedCats.value = makeSelectedWordCatList()
        }
    }

    private fun onDeleteConfirmed(confirmation: Boolean) {
        if(confirmation) {
            word.delete()
            findNavController().navigate(R.id.action_wordDetailedFragment_to_ViewPagerFragment)
            if(comingFromList != null) WordsListFragment.selectedWordCat.value = comingFromList
            ViewPagerMainFragment.comingFrom = Page.Words
        }
    }

    private fun onWordCatClicked(wordCat: WordCat) {
        wordCat.selected = !wordCat.selected
        if(!wordCat.selected) word.cats.remove(wordCat.id)
        else word.cats.add(wordCat.id)

        FireWordCats.update(word.id, "cats", word.cats)
        selectedCats.value = makeSelectedWordCatList()
        pickFirstWordCatColor()
    }

    private fun handleSelectedSphere(sphere: Sphere) {
        sphere.selected = !sphere.selected
        for(s in selectedSpheres.value!!) {
            if(sphere.id == s.id) s.selected = sphere.selected
        }

        //val newList = Helper.getResubmitList(sphere, selectedSpheres.value!!.toMutableList())
        val spheresList = selectedSpheres.value!!.toMutableList()
        selectedSpheres.value = spheresList
        val selectedSpheres = mutableListOf<Long>()
        for(s in spheresList) {
            if(s.selected) selectedSpheres.add(s.id)
        }
        FireWords.update(word.id, "spheres", selectedSpheres)
    }

    private fun getFilteredSnippetList() {
            val snippetsList = mutableListOf<Snippet>()
            for(snippet in Main.snippetList.value!!) {
                var contains = false
                for(w in snippet.getWords()) {
                    if(word.id == w.id) contains = true ; break
                }
                if(contains) snippetsList.add(snippet)
            }
            snippetAdapter.submitList(snippetsList)
        }

    private fun setRecycler() {
        snippetAdapter = SnippetAdapter(::snippetClickedFunc)
        b.wordDetailedRecycler.adapter = snippetAdapter

    }


    private fun snippetClickedFunc(snippet: Snippet) {

    }
}