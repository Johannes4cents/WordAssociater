package com.example.wordassociater.words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentHeritageBinding
import com.example.wordassociater.fams.FamRecycler
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.fire_classes.Stem
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.fire_classes.WordCat
import com.example.wordassociater.firestore.FireCommonWords
import com.example.wordassociater.firestore.FireFams
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.live_recycler.LiveRecycler
import com.example.wordassociater.popups.popConfirmation
import com.example.wordassociater.popups.popFamPicker
import com.example.wordassociater.popups.popNewStemOrFam
import com.example.wordassociater.utils.CommonWord
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.Language
import com.example.wordassociater.utils.LiveClass

class HeritageFragment: Fragment() {
    lateinit var b : FragmentHeritageBinding

    companion object {
        lateinit var word: Word
        lateinit var comingFrom: Frags
        var comingFromList : WordCat? = null
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = FragmentHeritageBinding.inflate(inflater)
        setContent()
        setClickListener()
        setRecycler()
        word.copyMe()
        return b.root
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
            findNavController().navigate(R.id.action_heritageFragment_to_wordDetailedFragment)
        }

        b.topBar.setLeftBtn {
            resetListsOnBack()
            findNavController().navigate(R.id.action_heritageFragment_to_wordDetailedFragment)
        }

        b.btnAddFam.setOnClickListener {
            popNewStemOrFam(b.btnAddFam, ::onNewFamAdded, false)
        }

        b.btnAddStems.setOnClickListener {
            popNewStemOrFam(b.btnAddStems, ::onNewStemAdded, true)
        }

    }

    private fun resetListsOnBack() {
        word.famList = word.oldWord.famList.toMutableList()
        word.stemList = word.oldWord.stemList.toMutableList()
    }

    private fun onNewFamAdded(fam: LiveClass) {
        (fam as Fam)
        fam.word = word.id
        word.takeFam(fam)
    }

    private fun onNewStemAdded(stem: LiveClass) {
        stem as Stem
        stem.word = word.id
        word.takeStem(stem)
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
        b.famRecycler.initRecycler(FamRecycler.Type.List, word.liveFams, ::onFamClicked, ::onCreateNewWord, ::onMakeCommonWord)
        word.getFams()

        b.stemRecycler.initRecycler(LiveRecycler.Mode.Preview, LiveRecycler.Type.Stem, ::onStemClicked, word.liveStems)
        word.getStemsList()
    }

    private fun onFamClicked(fam: Fam) {
        popConfirmation(b.btnAddFam) {
            if(it) {
                (fam as Fam).delete()
                word.takeFam(fam)
            }
        }
    }


    private fun onStemClicked(stem: LiveClass) {
        popConfirmation(b.btnAddStems) {
            if(it) {
                (stem as Stem).delete()
                word.takeStem(stem)
            }
        }
    }

    private fun onMakeCommonWord(fam: Fam, type: CommonWord.Type) {
        val commonWord = CommonWord(Helper.stripWordLeaveWhiteSpace(fam.text), Language.German, type)
        if(Main.getCommonWord(Language.German, fam.text, type) == null ) FireCommonWords.add(commonWord)
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


    private fun updateWord() {
        word.updateFams()
        word.updateStems()
    }

}