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
import com.example.wordassociater.ViewPagerFragment
import com.example.wordassociater.databinding.FragmentWordDetailedBinding
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.Sphere
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.firestore.FireLists
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.popups.popSelectSphere
import com.example.wordassociater.snippets.SnippetAdapter
import com.example.wordassociater.strains.StrainAdapter
import com.example.wordassociater.utils.Page

class WordDetailedFragment: Fragment() {
    lateinit var b: FragmentWordDetailedBinding
    private val selectedSpheres = MutableLiveData<List<Sphere>?>()

    companion object {
        lateinit var word: Word
        lateinit var strainAdapter: StrainAdapter
        lateinit var snippetAdapter: SnippetAdapter
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
        b.wordBgImage.setBackgroundResource(word.getCatsList()[0].getBg())
        b.btnNewWord.visibility = View.GONE
        getSpheresList()
        selectSpheresInList()
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

    private fun setClickListener() {
        b.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_wordDetailedFragment_to_ViewPagerFragment)
            ViewPagerFragment.comingFrom = Page.Words
        }

        b.buttonStrains.setOnClickListener {
            b.wordDetailedRecycler.adapter = strainAdapter
            getFilteredStrainsList()
        }

        b.buttonSnippets.setOnClickListener {
            b.wordDetailedRecycler.adapter = snippetAdapter
            getFilteredSnippetList()
        }

        b.btnWordConnections.setOnClickListener {
            WordConnectionsFragment.word = word
            findNavController().navigate(R.id.action_wordDetailedFragment_to_wordConnectionsFragment)
        }

        b.btnHeritage.setOnClickListener {
            HeritageFragment.word = word
            findNavController().navigate(R.id.action_wordDetailedFragment_to_heritageFragment)
        }

        b.btnSpheres.setOnClickListener {
            popSelectSphere(b.btnSpheres, selectedSpheres, ::handleSelectedSphere)
        }
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

    private fun getFilteredStrainsList() {
        FireLists.fireStrainsList.get().addOnSuccessListener { docs ->
            val strainsList = mutableListOf<Strain>()
            for(doc in docs) {
                val strain = doc.toObject(Strain::class.java)
                var contains = false
                for(w in strain.getWords()) {
                    if(word.id == w.id) contains = true ; break
                }
                if(contains) strainsList.add(strain)
            }
            strainAdapter.submitList(strainsList)
        }
    }

    private fun getFilteredSnippetList() {
            val snippetsList = mutableListOf<Snippet>()
            for(snippet in Main.snippetList.value!!) {
                var contains = false
                Log.i("wordProb", "snippet is: ${snippet.content} | id is ${snippet.id}")
                for(w in snippet.getWords()) {
                    Log.i("wordProb", "word is: ${w.text} | type is ${w.getCatsList()[0]}")
                    if(word.id == w.id) contains = true ; break
                }
                if(contains) snippetsList.add(snippet)
            }
            snippetAdapter.submitList(snippetsList)
        }

    private fun setRecycler() {
        strainAdapter = StrainAdapter(::handleStrainClickedFunc)
        snippetAdapter = SnippetAdapter(::snippetClickedFunc)
        b.wordDetailedRecycler.adapter = strainAdapter

    }

    private fun handleStrainClickedFunc(strain: Strain) {

    }

    private fun snippetClickedFunc(snippet: Snippet) {

    }
}