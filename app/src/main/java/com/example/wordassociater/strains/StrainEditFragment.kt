package com.example.wordassociater.strains

import android.os.Bundle
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
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.FragmentEditStrainBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireStrains
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.popups.popCharacterSelector
import com.example.wordassociater.popups.popSearchWord
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.words.WordLinear

class StrainEditFragment: Fragment() {
    lateinit var b : FragmentEditStrainBinding

    companion object {
        lateinit var adapter: CharacterAdapter

        var strain = Strain(
                id = FireStats.getStoryPartId()
        )

        var comingFrom = Frags.START

        val popUpCharacterList = MutableLiveData<List<Character>>(listOf())

    }

    var wordsList = MutableLiveData<List<Word>>(WordLinear.selectedWords)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentEditStrainBinding.inflate(layoutInflater)

        setWordList()
        setClickListener()
        setContent()
        handleDeleteButton()
        handleRecycler()
        setObserver()
        handleWordIcon()
        Main.inFragment = Frags.WRITE
        return b.root
    }

    private fun setWordList() {
        if(comingFrom == Frags.START) wordsList.value = WordLinear.selectedWords
        if(comingFrom == Frags.READ) wordsList.value = strain.getWords()
    }

    private fun updateWordList() {
        b.strainWords.text = Helper.setWordsToString(strain.getWords())
    }

    private fun handleWordIcon() {
        if(wordsList.value!!.isEmpty()) {
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
            FireStrains.delete(StrainListFragment.openStrain.value!!)
            StrainListFragment.openStrain.value = null
            findNavController().navigate(R.id.action_writeFragment_to_readFragment)
        }
    }

    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            if(comingFrom == Frags.START) findNavController().navigate(R.id.action_writeFragment_to_startFragment)
            else if(comingFrom == Frags.READ) findNavController().navigate(R.id.action_writeFragment_to_readFragment)
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
            popSearchWord(it, ::handleWordSelected, wordsList)
        }

        b.characterBtn.setOnClickListener {
            popCharacterSelector(b.characterBtn, findNavController(), popUpCharacterList, ::handleSelectedCharacter)
        }

        b.wordIcon.setOnClickListener {
            popCharacterSelector(b.characterBtn, findNavController(), popUpCharacterList, ::handleSelectedCharacter)
        }
    }

    private fun handleSelectedCharacter(character: Character) {
        if(character.selected && !strain.getCharacters().contains(character)) {
           strain.characterList.add(character.id)
        }
        else strain.characterList.remove(character.id)

        popUpCharacterList.value = Helper.getResubmitList(character, popUpCharacterList.value!!)
    }

    private fun handleWordSelected(word: Word) {
        word.isPicked = !word.isPicked
        if(!word.isPicked) {
            val selectedWordsList = wordsList.value!!.toMutableList()
            selectedWordsList.remove(word)
            wordsList.value = selectedWordsList
        }
        else {
            var selectedWordsList = mutableListOf<Word>()
            if(wordsList.value != null) selectedWordsList = wordsList.value!!.toMutableList()
            selectedWordsList.add(word)
            wordsList.value = selectedWordsList
        }
    }

    private fun setContent() {
        b.strainInput.setText(strain.content)
        b.headerInput.setText(strain.header)

        b.strainWords.text = Helper.setWordsToString(wordsList.value!!)

        for(character in strain.getCharacters()) {
            character.selected = true
        }

        b.strainWords.text = Helper.setWordsToMultipleLines(strain.getWords())
    }

    private fun saveStrain() {
        if(b.strainInput.text.isNotEmpty()) {
            strain.content = b.strainInput.text.toString()
            strain.header = b.headerInput.text.toString()
            strain.wordList = Word.convertToIdList(WordLinear.selectedWords)
            for(w in strain.getWords()) {
                if(!w.strainsList.contains(strain.id)) w.strainsList.add(strain.id)
                FireWords.update(w, strainsList = w.strainsList)
            }

            FireStrains.add(strain, requireContext())

            for(w in wordsList.value!!) {
                FireWords.increaseWordUse(w)
            }

            WordLinear.wordList.clear()
            WordLinear.selectedWords.clear()
            CharacterAdapter.selectedCharacterList.clear()
            CharacterAdapter.selectedNameChars.clear()

            strain = Strain(id= FireStats.getStoryPartId())

            if(comingFrom == Frags.START) findNavController().navigate(R.id.action_writeFragment_to_startFragment)
            else if(comingFrom == Frags.READ) findNavController().navigate(R.id.action_writeFragment_to_readFragment)

            Helper.getIMM(requireContext()).hideSoftInputFromWindow(b.strainInput.windowToken, 0)
        }
        else {
            Toast.makeText(requireContext(), "Want to put some words \n in there buddy?", Toast.LENGTH_SHORT).show()
        }
    }



    private fun handleRecycler() {
        adapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        b.characterRecycler.adapter = adapter

        if(adapter.currentList.isNotEmpty() && adapter.currentList[0].imgUrl != "") {
            Glide.with(b.characterBtn.context).load(adapter.currentList[0].imgUrl).into(b.characterBtn)
        }

        adapter.submitList(strain.getCharacters())

    }

    private fun setObserver() {
        popUpCharacterList.observe(context as LifecycleOwner) {
            val selectedList = it.filter { c -> c.selected }
            adapter.submitList(selectedList)

            if(adapter.currentList.isNotEmpty() && adapter.currentList[0].imgUrl != "") {
                Glide.with(b.characterBtn.context).load(adapter.currentList[0].imgUrl).into(b.characterBtn)
            }
        }

        wordsList.observe(context as LifecycleOwner) {
            strain.wordList = Word.convertToIdList(it)
            updateWordList()
            handleWordIcon()
        }
    }
}