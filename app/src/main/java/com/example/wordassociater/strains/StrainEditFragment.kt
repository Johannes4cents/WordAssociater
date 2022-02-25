package com.example.wordassociater.strains

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.ViewPagerFragment
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.FragmentEditStrainBinding
import com.example.wordassociater.fire_classes.*
import com.example.wordassociater.firestore.FireChars
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireStrains
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.popups.popCharacterSelector
import com.example.wordassociater.popups.popDramaTypeSelection
import com.example.wordassociater.popups.popSearchWord
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.Page
import com.example.wordassociater.utils.SearchHelper
import com.example.wordassociater.words.WordLinear

class StrainEditFragment: Fragment() {
    lateinit var b : FragmentEditStrainBinding
    lateinit var previewAdapter: CharacterAdapter
    var selectedCharacterList = mutableListOf<Character>()

    companion object {

        var oldStrain = Strain()

        var strain = Strain(
                id = FireStats.getStoryPartId()
        )

        var comingFrom = Frags.START
        val popUpCharacterList = MutableLiveData<MutableList<Character>>(mutableListOf())

        fun clearStrain() {
            strain.wordList = mutableListOf()
            strain.characterList = mutableListOf()
            strain.header = "Strain"
            strain.content = ""
        }

    }

    var liveWordsList = MutableLiveData(WordLinear.selectedWords)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentEditStrainBinding.inflate(layoutInflater)
        setSelectedCharacterList()
        getWordsComingFromStart()
        setWordList()
        setWordIcon()
        setClickListener()
        setContent()
        handleDeleteButton()
        handleRecycler()
        setObserver()
        handleWordIcon()
        Main.inFragment = Frags.WRITE
        return b.root
    }

    private fun setSelectedCharacterList() {
        selectedCharacterList = strain.getCharacters().toMutableList()
    }

    private fun handleWordDeselected() {
        for(wordId in oldStrain.wordList) {
            if(!strain.wordList.contains(wordId)) {
                val word = Main.getWord(wordId)!!
                WordConnection.disconnect(word, strain.id)
                word.strainsList.remove(strain.id)
                word.decreaseWordUsed()
                FireWords.update(word.id, "strainsList", word.strainsList)
            }
        }
    }



    private fun handleCharacterDeselected() {
        for(charId in oldStrain.characterList) {
            if(!strain.characterList.contains(charId)) {
                val character = Main.getCharacter(charId)
                if(character != null) {
                    character.strainsList.remove(strain.id)
                    FireChars.update(character.id, "strainsList", character.strainsList)
                }
            }
        }
    }

    private fun setWordIcon() {
        if(strain.wordList.isEmpty()) {
            b.wordIcon.visibility = View.VISIBLE
            b.strainWords.visibility = View.GONE
        }
        else {
            b.wordIcon.visibility = View.GONE
            b.strainWords.visibility = View.VISIBLE
        }
    }

    private fun getWordsComingFromStart() {
        if(comingFrom == Frags.START) strain.wordList = Word.convertToIdList(WordLinear.selectedWords)
    }

    private fun setWordList() {
        SearchHelper.setWordList(strain.getWords(), liveWordsList)
    }

    private fun updateWordList() {
        b.strainWords.text = Helper.setWordsToString(strain.getWords())
    }

    private fun handleWordIcon() {
        if(strain.wordList.isEmpty()) {
            b.wordIcon.visibility = View.VISIBLE
            b.strainWords.visibility = View.GONE
        }
        else {
            b.wordIcon.visibility = View.GONE
            b.strainWords.visibility = View.VISIBLE
        }
    }


    private fun handleDeleteButton() {
        if(StrainListFragment.openStrain.value != null) {
            b.deleteBtn.visibility = View.VISIBLE
        }
        else {
            b.deleteBtn.visibility = View.GONE
        }

        b.deleteBtn.setOnClickListener {
            Pop(b.deleteBtn.context).confirmationPopUp(b.deleteBtn, ::deleteStrain)
        }
    }

    private fun deleteStrain(confirmed: Boolean) {
        if(confirmed) {
            strain.delete()
            StrainListFragment.openStrain.value = null
            findNavController().navigate(R.id.action_writeFragment_to_readFragment)
        }
    }

    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            strain.wordList = oldStrain.wordList
            strain.characterList = oldStrain.characterList
            if(comingFrom == Frags.START) {
                ViewPagerFragment.comingFrom = Page.Start
                findNavController().navigate(R.id.action_writeFragment_to_startFragment)
            }
            else if(comingFrom == Frags.READ) {
                cleanUp()
                findNavController().navigate(R.id.action_writeFragment_to_readFragment)
            }
        }

        b.saveBtn.setOnClickListener {
            saveStrain()
        }

        b.strainInput.setOnClickListener {
            it.isFocusable = true
            it.isFocusableInTouchMode = true
            Helper.getIMM(requireContext()).showSoftInput(it, 0)
        }

        b.strainWords.setOnClickListener {
            popSearchWord(it, ::handleWordSelected, liveWordsList)
        }

        b.characterBtn.setOnClickListener {
            popCharacterSelector(b.characterBtn, popUpCharacterList, ::handleSelectedCharacter)
        }

        b.wordIcon.setOnClickListener {
            popSearchWord(it, ::handleWordSelected, liveWordsList)
        }

        b.btnDrama.setOnClickListener {
            popDramaTypeSelection(b.btnDrama, ::onDramaSelected)
        }


    }

    private fun onDramaSelected(drama: Drama.Type) {
        b.btnDrama.setImageResource(Drama.getImage(drama))
        strain.drama = drama
    }

    private fun handleSelectedCharacter(character: Character) {
        character.selected = !character.selected

        if(character.selected && !selectedCharacterList.contains(character)) {
           selectedCharacterList.add(character)
        }
        else selectedCharacterList.remove(character)

        popUpCharacterList.value = Helper.getResubmitList(character, popUpCharacterList.value!!)
    }

    private fun handleWordSelected(word: Word) {
        word.selected = !word.selected
        if(word.selected) {
            if(!strain.wordList.contains(word.id)) strain.wordList.add(word.id)
        }
        else strain.wordList.remove(word.id)
        SearchHelper.setWordList(strain.getWords(), liveWordsList)
    }

    private fun setContent() {
        b.strainInput.setText(strain.content)
        b.headerInput.setText(strain.header)

        b.strainWords.text = Helper.setWordsToString(liveWordsList.value!!)

        for(character in strain.getCharacters()) {
            character.selected = true
        }

        b.strainWords.text = Helper.setWordsToMultipleLines(strain.getWords())
    }

    private fun saveStrain() {

        val selectedWords = mutableListOf<Word>()
        for(w in liveWordsList.value!!) {
            if(w.selected && !selectedWords.contains(w)) selectedWords.add(w)
        }

        if(b.strainInput.text.isNotEmpty()) {
            strain.content = b.strainInput.text.toString()
            strain.header = b.headerInput.text.toString()
            strain.wordList = Word.convertToIdList(selectedWords)
            strain.characterList = Character.getIdList(selectedCharacterList)


            FireStrains.add(strain, requireContext())

            for(w in selectedWords) {
                if(!w.strainsList.contains(strain.id)) w.strainsList.add(strain.id)
                FireWords.update(w.id, "strainsList", w.strainsList)
                if(!oldStrain.wordList.contains(w.id)) w.increaseWordUsed()
            }

            for(c in strain.getCharacters()) {
                if(!c.strainsList.contains(strain.id)) {
                    c.strainsList.add(strain.id)
                    FireChars.update(c.id, "strainsList", c.strainsList)
                }
            }

            WordConnection.connect(strain)
            handleWordDeselected()
            handleCharacterDeselected()

            Log.i("characterProb", "strain.characterlist :  ${strain.characterList}")
            cleanUp()

            if(comingFrom == Frags.START) {
                ViewPagerFragment.comingFrom = Page.Start
                findNavController().navigate(R.id.action_writeFragment_to_startFragment)
            }
            else if(comingFrom == Frags.READ) findNavController().navigate(R.id.action_writeFragment_to_readFragment)


        }
        else {
            Toast.makeText(requireContext(), "Want to put some words \n in there buddy?", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cleanUp() {

        val newCharList = mutableListOf<Character>()
        val charList = Main.characterList.value!!.toMutableList()
        for(c in charList) {
            c.selected = false
            newCharList.add(c)
        }

        popUpCharacterList.value = charList
        WordLinear.wordList.clear()
        WordLinear.selectedWords.clear()
        CharacterAdapter.selectedCharacterList.clear()
        CharacterAdapter.selectedNameChars.clear()
        strain = Strain(id= FireStats.getStoryPartId())
    }


    private fun handleRecycler() {
        previewAdapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        b.characterRecycler.adapter = previewAdapter

        if(previewAdapter.currentList.isNotEmpty() && previewAdapter.currentList[0].imgUrl != "") {
            Glide.with(b.characterBtn.context).load(previewAdapter.currentList[0].imgUrl).into(b.characterBtn)
        }

        previewAdapter.submitList(strain.getCharacters())

    }

    private fun setObserver() {
        popUpCharacterList.observe(context as LifecycleOwner) {
            val selectedList = it.filter { c -> c.selected }

            previewAdapter.submitList(selectedList)

            if(selectedList.isNotEmpty() && selectedList[0].imgUrl != "") {
                Glide.with(b.characterBtn.context).load(selectedList[0].imgUrl).into(b.characterBtn)
            }
            if(selectedList.isEmpty()) {
                b.characterBtn.setImageResource(R.drawable.icon_edit_character)
            }
        }

        liveWordsList.observe(context as LifecycleOwner) {
            updateWordList()
            handleWordIcon()
        }
    }
}