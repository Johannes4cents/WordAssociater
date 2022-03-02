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
import com.example.wordassociater.databinding.FragmentHeritageBinding
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.firestore.FireCommonWords
import com.example.wordassociater.firestore.FireFams
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.popups.popFamPicker
import com.example.wordassociater.fams.FamRecycler
import com.example.wordassociater.utils.CommonWord
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.Language
import java.util.*

class HeritageFragment: Fragment() {
    lateinit var b : FragmentHeritageBinding

    private val famLiveList = MutableLiveData<List<Fam>>(listOf())
    private val stemsLiveList = MutableLiveData<List<String>>(listOf())
    companion object {
        lateinit var oldWord: Word
        lateinit var word: Word
        lateinit var comingFrom: Frags
        var comingFromList : WordCat? = null
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = FragmentHeritageBinding.inflate(inflater)
        setOldWord()
        setContent()
        setClickListener()
        setRecycler()
        setObserver()

        return b.root
    }

    private fun setOldWord() {
        oldWord = word.copyMe()
    }

    private fun setContent() {
        b.nameText.text = word.getMainFam()?.text ?: "No Main set"
    }

    private fun setClickListener() {
        b.nameText.setOnClickListener {
            popFamPicker(b.nameText, word, ::onFamPickedForNewName, word.getFams())
        }

        b.topBar.setBtn1IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn2IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn3IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn4IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn5IconAndVisibility(R.drawable.icon_word, false)

        b.topBar.setRightButton {
            updateWord()
            stemsLiveList.value = mutableListOf()
            famLiveList.value = mutableListOf()
            findNavController().navigate(R.id.action_heritageFragment_to_wordDetailedFragment)
        }

        b.topBar.setLeftBtn {
            checkFamsOnBack()
            stemsLiveList.value = mutableListOf()
            famLiveList.value = mutableListOf()
            findNavController().navigate(R.id.action_heritageFragment_to_wordDetailedFragment)
        }

    }

    private fun checkFamsOnBack() {
        for(fam in famLiveList.value!!) {
            if(!oldWord.famList.contains(fam.id)) {
                word.famList.remove(fam.id)
                FireFams.delete(fam.id)
            }
        }
    }

    private fun onFamPickedForNewName(fam: Fam) {
        b.nameText.text = fam.text
        for(f in word.getFams()) {
            if(f.main) {
                f.main = false
                FireFams.update(fam.id, "main", fam.main)
            }
        }
        fam.main = true
        FireFams.update(fam.id, "main", fam.main)
        word.text = fam.text
    }

    private fun setRecycler() {
        famLiveList.value = word.getFams()
        b.famRecycler.initRecycler(FamRecycler.Type.List, word, famLiveList, ::onFamHeaderClicked, ::onFamEntered, ::onCreateNewWord, ::onMakeCommonWord)

        stemsLiveList.value = word.stems
        b.stemsRecycler.initRecycler(word, stemsLiveList, ::onStemHeaderClicked, ::onStemEntered)

    }

    private fun onFamHeaderClicked() {
        val newFam = Fam(id = FireStats.getFamNumber())
        famLiveList.value = listOf(newFam) + word.getFams()
        b.famRecycler.adapter?.notifyDataSetChanged()
    }

    private fun onFamEntered(fam: Fam) {
        if(fam.text != "" && fam.text != " " && fam.checkIfWord() == null) {
            fam.text = Helper.stripWordLeaveWhiteSpace(fam.text)
            fam.word = word.id
            fam.firstOpen = true
            word.famList.add(fam.id)
            FireFams.add(fam)
            b.famRecycler.adapter?.notifyDataSetChanged()
        }
        else if(word.checkIfFamExists(fam.text) != null) {
            Helper.toast("That already is it's own Word (${word.text} (${word.id}))", requireContext())
        }
    }

    private fun onMakeCommonWord(fam: Fam, type: CommonWord.Type) {
        val commonWord = CommonWord(Helper.stripWordLeaveWhiteSpace(fam.text), Language.German, type)
        if(Main.getCommonWord(Language.German, fam.text) == null ) FireCommonWords.add(commonWord)
        else Helper.toast("Already a common Word", b.root.context)
        fam.commonWord = type
    }

    private fun onCreateNewWord(fam: Fam) {
        val word = Main.getWordByText(Helper.stripWordLeaveWhiteSpace(fam.text))
        if(word == null) {
            val newWord = Word(id = FireStats.getWordId(), text = fam.text )
            newWord.famList.add(fam.id)
            fam.word = newWord.id
            FireFams.update(fam.id, "word", fam.word)
        }
        else {
            Helper.toast("That is already it's own Word", requireContext())
        }
    }

    private fun onStemHeaderClicked() {
        word.stems.remove("stemHeader")
        word.stems.remove("StemHeader")
        word.stems.remove("")
        word.stems.remove(" ")
        stemsLiveList.value = word.stems + listOf("")
        b.stemsRecycler.adapter?.notifyDataSetChanged()
    }

    private fun onStemEntered(text: String) {
        val strippedWord = Helper.stripWord(text).capitalize(Locale.ROOT)
        if(strippedWord != " " && text != "" && !word.stems.contains(strippedWord) && text != "stemHeader" && text != "StemHeader") {
            word.stems.add(strippedWord)
            stemsLiveList.value = word.stems
            b.stemsRecycler.adapter?.notifyDataSetChanged()
        }
    }

    private fun updateWord() {
        FireWords.update(word.id, "stems", word.stems)
        FireWords.update(word.id, "text", word.text)
        FireWords.update(word.id, "famList", word.famList)
    }

    private fun setObserver() {
        Main.famList.observe(requireContext() as LifecycleOwner) {
            val famList = mutableListOf<Fam>()
            if (it != null) {
                for(fam in it) {
                    if(fam.word == word.id) famList.add(fam)
                }
            }
            famLiveList.value = famList
        }
    }



}