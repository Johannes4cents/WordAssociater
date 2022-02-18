package com.example.wordassociater.strain_edit_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.FragmentEditStrainBinding
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.firestore.FireLists
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireStrains
import com.example.wordassociater.firestore.FireWords
import com.example.wordassociater.popups.Pop
import com.example.wordassociater.start_fragment.WordLinear
import com.example.wordassociater.story.Story
import com.example.wordassociater.strain_list_fragment.StrainListFragment
import com.example.wordassociater.utils.Helper

class StrainEditFragment: Fragment() {
    lateinit var b : FragmentEditStrainBinding


    companion object {
        lateinit var adapter: CharacterAdapter
    }

    var wordsList = StrainListFragment.openStrain.value?.getWords() ?: WordLinear.selectedWords
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentEditStrainBinding.inflate(layoutInflater)
        Helper.setWords(wordsList, b.associatedWords)
        setClickListener()
        setContent()
        handleDeleteButton()
        handleRecycler()
        handleCharacter()
        Main.inFragment = Frags.WRITE
        return b.root
    }

    private fun handleCharacter() {
        if(adapter.currentList.isNotEmpty() && adapter.currentList[0].imgUrl != "") {
            Glide.with(b.characterBtn.context).load(adapter.currentList[0].imgUrl).into(b.characterBtn)
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
            if(StrainListFragment.openStrain.value == null) {
                findNavController().navigate(R.id.action_writeFragment_to_startFragment)
            }
            else {
                StrainListFragment.openStrain.value = null
                findNavController().navigate(R.id.action_writeFragment_to_readFragment)
            }
        }

        b.saveBtn.setOnClickListener {
            if(StrainListFragment.openStrain.value == null) {
                saveStrain()
            }
            else {
                updateStrain()
            }

        }

        b.strainInput.setOnClickListener {
            it.isFocusable = true
            it.isFocusableInTouchMode = true
            Helper.getIMM(requireContext()).showSoftInput(it, 0)
        }

        b.associatedWords.setOnClickListener {

        }

        if(StrainListFragment.openStrain.value != null) {
            b.characterBtn.setOnClickListener {
                Pop(b.characterBtn.context).characterRecycler(b.characterBtn, CharacterAdapter.Mode.UPDATE)
            }
        }
        else {
            b.characterBtn.setOnClickListener {
                Pop(b.characterBtn.context).characterRecycler(b.characterBtn, CharacterAdapter.Mode.SELECT)
            }
        }
    }

    private fun setContent() {
        if(StrainListFragment.openStrain.value != null) {
            b.strainInput.setText(StrainListFragment.openStrain.value?.content!!.toString())
            b.headerInput.setText(StrainListFragment.openStrain.value?.header)
        }
    }

    private fun saveStrain() {
        if(b.strainInput.text.isNotEmpty()) {

            var header = b.headerInput.text ?: "Strain"
            var strain = Strain(
                    b.strainInput.text.toString(),
                    Word.convertToIdList(WordLinear.selectedWords), header.toString(),
                    id = FireStats.getStoryPartNumber()
            )

            for(w in WordLinear.selectedWords) {
                w.strainsList.add(strain.id)
                FireWords.update(w, strainsList = w.strainsList)
            }
            if(Story.storyModeActive) strain.isStory = true
            for(char in CharacterAdapter.selectedCharacterList) {
                char.selected = false
                strain.characterList.add(char)
            }
            FireStrains.add(strain, requireContext())

            for(w in wordsList) {
                FireWords.increaseWordUse(w)
            }

            WordLinear.wordList.clear()
            WordLinear.selectedWords.clear()
            CharacterAdapter.selectedCharacterList.clear()
            CharacterAdapter.selectedNameChars.clear()

            findNavController().navigate(R.id.action_writeFragment_to_startFragment)
            Helper.getIMM(requireContext()).hideSoftInputFromWindow(b.strainInput.windowToken, 0)
        }
        else {
            Toast.makeText(requireContext(), "Want to put some words \n in there buddy?", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateStrain() {
        var header = b.headerInput.text ?: "Strain"
        var strain = Strain(b.strainInput.text.toString(), StrainListFragment.openStrain.value!!.wordList, header.toString())
        if(StrainListFragment.openStrain.value?.characterList != null) {
            for(char in StrainListFragment.openStrain.value?.characterList!!) {
                char.selected = false
                strain.characterList.add(char)
            }
        }
        FireLists.fireStrainsList.document(StrainListFragment.openStrain.value!!.id.toString()).set(strain)
        WordLinear.selectedWords.clear()
        findNavController().navigate(R.id.action_writeFragment_to_readFragment)
        Helper.getIMM(requireContext()).hideSoftInputFromWindow(b.strainInput.windowToken, 0)
    }

    private fun handleRecycler() {
        adapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        b.characterRecycler.adapter = adapter
        if(StrainListFragment.openStrain.value == null) {
            adapter.submitList(CharacterAdapter.selectedCharacterList)
        }
        else {
            adapter.submitList(StrainListFragment.openStrain.value?.characterList)
        }

    }
}