package com.example.wordassociater

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.bars.AddWordBar
import com.example.wordassociater.databinding.FragmentStartBinding
import com.example.wordassociater.fire_classes.Sphere
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.firestore.FireWordCats
import com.example.wordassociater.popups.popConfirmation
import com.example.wordassociater.popups.popWordCatAllOptions
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.wordcat.WordCatAdapter
import com.example.wordassociater.words.WordLinear

class StartFragment: Fragment() {
    lateinit var b : FragmentStartBinding


    companion object {
        val selectedWordCats = MutableLiveData<List<WordCat>>()
        val selectedSphereList = MutableLiveData<List<Sphere>>()

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Main.inFragment = Frags.START
        b = FragmentStartBinding.inflate(inflater)
        b.contentBar.navController = findNavController()
        b.addStuffBar.navController = findNavController()
        AddWordBar.navController = findNavController()
        setRecycler()
        setPreviewBar()

        return b.root
    }

    private fun setPreviewBar() {
        b.addStuffBar.handlePreviewBar(b.previewBar)
    }


    private fun setRecycler() {
        b.connectedWordRecycler.initRecycler(WordLinear.selectedWordsLive)
        b.wordCatRecycler.setupRecycler(WordCatAdapter.Type.BTN, ::onWordCatClicked,  selectedWordCats, ::onWordCatHeaderClicked)
    }

    private fun onWordCatClicked(wordCat: WordCat) {
        Log.i("WordCatProb", "StartFragment - onCatClicked | wordCat is $wordCat")
        val word = WordLinear.getWord(wordCat)
        if(word != null) WordLinear.wordList.add(word)
        WordLinear.wordListTrigger.value = Unit
    }

    private fun onWordCatDeleteClicked(wordCat: WordCat) {
        popConfirmation(b.root) { confirmation ->
            if(confirmation) {
                wordCat.delete()
                val newList = selectedWordCats.value!!.toMutableList()
                newList.remove(wordCat)
                selectedWordCats.value = newList
            }
        }
    }

    private fun onWordCatHeaderClicked() {
        popWordCatAllOptions(b.wordCatRecycler, selectedWordCats, ::onHeaderWordCatSelected, ::onWordCatDeleteClicked)
    }

    private fun onHeaderWordCatSelected(wordCat: WordCat) {
        wordCat.active = !wordCat.active
        FireWordCats.update(wordCat.id, "active", wordCat.active)
        selectedWordCats.value = Helper.getResubmitList(wordCat, selectedWordCats.value!!)
    }

}